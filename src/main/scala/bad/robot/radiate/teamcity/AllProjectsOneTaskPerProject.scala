package bad.robot.radiate.teamcity

import bad.robot.radiate.RadiateError.Error
import bad.robot.radiate.monitor.{MonitoringTask, MonitoringTasksFactory, ThreadSafeObservable}
import bad.robot.radiate.teamcity.AllProjectsOneTaskPerProject._
import scalaz.\/
import scalaz.syntax.either._

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
  def create: Error \/ List[MonitoringTask] = {
    val configuration = YmlConfiguration.loadOrCreate(new BootstrapTeamCity, this)
    val teamcity = createTeamCity(configuration)

    for {
      all       <- teamcity.retrieveProjects
      projects  <- configuration.filter(all).right
      full      <- teamcity.retrieveFullProjects(projects)
      filtered  <- full.filter(nonEmpty).right
      tasks     <- filtered.map(toTasks(configuration)).right
    } yield tasks
  }

  override def toString = "monitoring multiple projects"
}