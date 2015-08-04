package bad.robot.radiate.teamcity

import bad.robot.radiate.monitor.{MonitoringTask, MonitoringTasksFactory, ThreadSafeObservable}
import bad.robot.radiate.teamcity.AllProjectsOneTaskPerProject._

/** @see [[bad.robot.radiate.monitor.MonitoringTasksFactory.multipleProjects]] */
object AllProjectsOneTaskPerProject {
  private def createTeamCity(configuration: TeamCityConfiguration): TeamCity = {
    val server = new Server(configuration.host, configuration.port)
    new TeamCity(server, configuration.authorisation, new HttpClientFactory().create(configuration), new JsonProjectsUnmarshaller, new JsonProjectUnmarshaller, new JsonBuildUnmarshaller)
  }

  private def toTasks(configuration: TeamCityConfiguration): Project => MonitoringTask = {
    project => new SingleProjectMonitor(project, configuration)
  }

  private def nonEmpty: Project => Boolean = _.buildTypes.nonEmpty
}

class AllProjectsOneTaskPerProject extends ThreadSafeObservable with MonitoringTasksFactory {
  def create: List[MonitoringTask] = {
    val configuration = YmlConfiguration.loadOrCreate(new BootstrapTeamCity, this)
    val teamcity = createTeamCity(configuration)
    val projects = configuration.filter(teamcity.retrieveProjects)
    teamcity.retrieveFullProjects(projects).filter(nonEmpty).map(toTasks(configuration)).toList
  }

  override def toString = "monitoring multiple projects"
}