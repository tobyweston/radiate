package bad.robot.radiate.teamcity

import bad.robot.radiate.monitor.{MonitoringTaskS, MonitoringTasksFactoryS, ThreadSafeObservableS}
import bad.robot.radiate.teamcity.AllProjectsOneTaskPerProjectS._

/** @see [[bad.robot.radiate.monitor.MonitoringTasksFactoryS.multipleProjects]] */
object AllProjectsOneTaskPerProjectS {
  private def createTeamCity(configuration: TeamCityConfigurationS): TeamCityS = {
    val server = new ServerS(configuration.host, configuration.port)
    new TeamCityS(server, configuration.authorisation, new HttpClientFactoryS().create(configuration), new JsonProjectsUnmarshallerS, new JsonProjectUnmarshallerS, new JsonBuildUnmarshallerS)
  }

  private def toTasks(configuration: TeamCityConfigurationS): FullProjectS => MonitoringTaskS = {
    project => new SingleProjectMonitorS(project, configuration)
  }

  private def nonEmpty: FullProjectS => Boolean = _.buildTypes.nonEmpty
}

class AllProjectsOneTaskPerProjectS extends ThreadSafeObservableS with MonitoringTasksFactoryS {
  def create: List[MonitoringTaskS] = {
    val configuration = YmlConfigurationS.loadOrCreate(new BootstrapTeamCityS, this)
    val teamcity = createTeamCity(configuration)
    val projects = configuration.filter(teamcity.retrieveProjects)
    teamcity.retrieveFullProjects(projects).filter(nonEmpty).map(toTasks(configuration)).toList
  }

  override def toString = "monitoring multiple projects"
}