package bad.robot.radiate.teamcity

import bad.robot.radiate.Aggregator.aggregate
import bad.robot.radiate.monitor.{Information, MonitoringTask, NonRepeatingObservable}

class AllProjectsMonitor(configuration: TeamCityConfiguration) extends NonRepeatingObservable with MonitoringTask {
  private val http = new HttpClientFactory().create(configuration)
  private val server = new Server(configuration.host, configuration.port)
  private val teamcity = new TeamCity(server, configuration.authorisation, http, new JsonProjectsUnmarshaller, new JsonProjectUnmarshaller, new JsonBuildUnmarshaller)

  private var monitored = List("unknown")

  def run {
    try {
      val projects = configuration.filter(teamcity.retrieveProjects)
      monitored = projects.map(_.toString).toList
      val buildTypes = teamcity.retrieveBuildTypes(projects)
      val builds = buildTypes.par.map(teamcity.retrieveLatestBuild).toList
      val aggregated = aggregate(builds)
      notifyObservers(aggregated.activity, aggregated.progress)
      notifyObservers(aggregated.status)
      notifyObservers(new Information(toString))
    } catch {
      case e: Exception => notifyObservers(e)
    }
  }

  override def toString = s"monitoring ${monitored.mkString("\r\n")} as a single aggregate"
}