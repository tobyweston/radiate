package bad.robot.radiate.teamcity

import bad.robot.radiate.Aggregator.aggregate
import bad.robot.radiate.monitor.{Information, MonitoringTask, NonRepeatingObservable}

class SingleProjectMonitor(project: Project, configuration: TeamCityConfiguration) extends NonRepeatingObservable with MonitoringTask {
  private val http = new HttpClientFactory().create(configuration)
  private val server = new Server(configuration.host, configuration.port)
  private val teamcity = new TeamCity(server, configuration.authorisation, http, new JsonProjectsUnmarshaller, new JsonProjectUnmarshaller, new JsonBuildUnmarshaller)

  def run {
    try {
      val buildTypes = teamcity.retrieveBuildTypes(List(project))
      val builds = buildTypes.par.map(teamcity.retrieveLatestBuild).toList
      val aggregated = aggregate(builds)
      notifyObservers(aggregated.activity, aggregated.progress)
      notifyObservers(aggregated.status)
      notifyObservers(new Information(toString))
    } catch {
      case e: Exception => notifyObservers(e)
    }
  }

  override def toString = s"${project.name} (${project.id})"
}