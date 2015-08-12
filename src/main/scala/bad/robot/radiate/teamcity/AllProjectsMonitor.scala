package bad.robot.radiate.teamcity

import bad.robot.radiate.Aggregator.aggregate
import bad.robot.radiate.monitor.{Information, MonitoringTask, NonRepeatingObservable}
import bad.robot.radiate.teamcity.ListBuildTypesSyntax._

import scalaz.syntax.either._

class AllProjectsMonitor(configuration: TeamCityConfiguration) extends NonRepeatingObservable with MonitoringTask {

  private val http = new HttpClientFactory().create(configuration)
  private val server = new Server(configuration.host, configuration.port)
  private val teamcity = new TeamCity(server, configuration.authorisation, http, new JsonProjectsUnmarshaller, new JsonProjectUnmarshaller, new JsonBuildUnmarshaller)

  private var monitored = List("unknown")

  def run() {
    val builds = for {
      all         <- teamcity.retrieveProjects
      projects    <- configuration.filter(all).right
      monitored   <- projects.map(_.toString).right                                              /* side affect, use = */
      buildTypes  <- teamcity.retrieveBuildTypes(projects)
      builds      <- buildTypes.getBuilds(teamcity)
      aggregation <- aggregate(builds).right
    } yield aggregation

//    this.monitored = monitored

    builds.fold(errors => {
      notifyObservers(errors)
    }, aggregated => {
      notifyObservers(aggregated.activity, aggregated.progress)
      notifyObservers(aggregated.status)
      notifyObservers(new Information(toString))
    })
  }

  override def toString = s"monitoring ${monitored.mkString("\r\n")} as a single aggregate"
}