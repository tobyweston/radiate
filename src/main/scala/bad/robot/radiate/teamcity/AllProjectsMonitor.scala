package bad.robot.radiate.teamcity

import bad.robot.radiate.Aggregate.aggregate
import bad.robot.radiate.config.KnobsConfig
import bad.robot.radiate.config.Config
import bad.robot.radiate.monitor.{Information, MonitoringTask, NonRepeatingObservable}
import KnobsConfig._
import bad.robot.radiate.teamcity.ListBuildTypesSyntax._

import scalaz.syntax.either._

class AllProjectsMonitor extends NonRepeatingObservable with MonitoringTask {

  private var monitored = List("unknown")

  def run() {
    val builds = for {
      file        <- load() orElse create
      config      <- Config(file)
      _           <- store(file)
      http         = HttpClientFactory().create(config)
      teamcity     = TeamCity(TeamCityUrl(config.url), config.authorisation, http, new JsonProjectsUnmarshaller, new JsonProjectUnmarshaller, new JsonBuildUnmarshaller)
      all         <- teamcity.retrieveProjects
      projects    <- all.filter(project => config.projects.contains(project.id)).right
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