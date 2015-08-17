package bad.robot.radiate.teamcity

import bad.robot.radiate.Aggregate.aggregate
import bad.robot.radiate.monitor.{Information, MonitoringTask, NonRepeatingObservable}
import bad.robot.radiate.teamcity.KnobsConfiguration._
import bad.robot.radiate.teamcity.ListBuildTypesSyntax._

import scalaz.syntax.either._

class AllProjectsMonitor extends NonRepeatingObservable with MonitoringTask {

  private val server = TeamCityUrl(_)

  private var monitored = List("unknown")

  def run() {
    val builds = for {
      config      <- load orElse create
      url         <- config.serverUrl
      http        = HttpClientFactory().create(config)
      teamcity    = TeamCity(server(url), config.authorisation, http, new JsonProjectsUnmarshaller, new JsonProjectUnmarshaller, new JsonBuildUnmarshaller)
      all         <- teamcity.retrieveProjects
      projects    <- config.filter(all).right
      monitored   <- projects.map(_.toString).toList.right
      buildTypes  <- teamcity.retrieveBuildTypes(projects)
      builds      <- buildTypes.getBuilds(teamcity)
      aggregation <- aggregate(builds).right
    } yield (monitored, aggregation)

    builds.fold(errors => {
      notifyObservers(errors)
    }, {
      case (monitored, aggregated) => {
        this.monitored = monitored
        notifyObservers(aggregated.activity, aggregated.progress)
        notifyObservers(aggregated.status)
        notifyObservers(new Information(toString))
      }
    })
  }

  override def toString = s"monitoring ${monitored.mkString("\r\n")} as a single aggregate"
}