package bad.robot.radiate.teamcity

import bad.robot.radiate.Sequence._
import bad.robot.radiate.{AggregateError, Sequence, Aggregator}
import bad.robot.radiate.Aggregator.aggregate
import bad.robot.radiate.monitor.{Information, MonitoringTask, NonRepeatingObservable}
import scalaz.\/
import scalaz.syntax.either._
import bad.robot.radiate.RadiateError.Error

class AllProjectsMonitor(configuration: TeamCityConfiguration) extends NonRepeatingObservable with MonitoringTask {
  private val http = new HttpClientFactory().create(configuration)
  private val server = new Server(configuration.host, configuration.port)
  private val teamcity = new TeamCity(server, configuration.authorisation, http, new JsonProjectsUnmarshaller, new JsonProjectUnmarshaller, new JsonBuildUnmarshaller)

  private var monitored = List("unknown")

  def run() {
    val (monitored, builds) = for {
      all         <- teamcity.retrieveProjects
      projects    <- configuration.filter(all).right[Error]
      monitored   <- projects.map(_.toString).right[Error]                                              /* side affect, use = */
      buildTypes  <- teamcity.retrieveBuildTypes(projects)
      builds      <- getBuilds(buildTypes)
      aggregation <- aggregate(builds).right[Error]
    } yield (monitored, aggregation)

    this.monitored = monitored

    builds.fold(errors => {
      notifyObservers(errors)
    }, aggregated => {
      notifyObservers(aggregated.activity, aggregated.progress)
      notifyObservers(aggregated.status)
      notifyObservers(new Information(toString))
    })

    def getBuilds(buildTypes: List[BuildType]): Error \/ List[Build] = {
      sequence(buildTypes.par.map(teamcity.retrieveLatestBuild).toList).leftMap(AggregateError)
    }
  }

  override def toString = s"monitoring ${monitored.mkString("\r\n")} as a single aggregate"
}