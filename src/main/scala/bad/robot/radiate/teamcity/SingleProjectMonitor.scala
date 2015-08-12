package bad.robot.radiate.teamcity

import bad.robot.radiate.Aggregator.aggregate
import bad.robot.radiate.monitor.{Information, MonitoringTask, NonRepeatingObservable}
import bad.robot.radiate.teamcity.ListBuildTypesSyntax._

import scalaz.syntax.either._

class SingleProjectMonitor(project: Project, configuration: TeamCityConfiguration) extends NonRepeatingObservable with MonitoringTask {
  private val http = new HttpClientFactory().create(configuration)
  private val server = new Server(configuration.host, configuration.port)
  private val teamcity = new TeamCity(server, configuration.authorisation, http, new JsonProjectsUnmarshaller, new JsonProjectUnmarshaller, new JsonBuildUnmarshaller)

  def run() {
    val builds = for {
      buildTypes  <- teamcity.retrieveBuildTypes(List(project))
      builds      <- buildTypes.getBuilds(teamcity)
      aggregation <- aggregate(builds).right
    } yield aggregation

    builds.fold(error => {
      notifyObservers(error)
    }, aggregation => {
      notifyObservers(aggregation.activity, aggregation.progress)
      notifyObservers(aggregation.status)
      notifyObservers(new Information(toString))
    })
  }

  override def toString = s"${project.name} (${project.id})"
}