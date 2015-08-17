package bad.robot.radiate.teamcity

import bad.robot.radiate.Aggregate.aggregate
import bad.robot.radiate.monitor.{Information, MonitoringTask, NonRepeatingObservable}
import bad.robot.radiate.teamcity.ListBuildTypesSyntax._

import scalaz.syntax.either._

class SingleProjectMonitor(project: Project, configuration: TeamCityConfiguration) extends NonRepeatingObservable with MonitoringTask {
  private val http = new HttpClientFactory().create(configuration)
  private val server = new TeamCityUrl(_)

  def run() {
    val builds = for {
      url         <- configuration.serverUrl
      teamcity    = new TeamCity(server(url), configuration.authorisation, http, new JsonProjectsUnmarshaller, new JsonProjectUnmarshaller, new JsonBuildUnmarshaller)
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