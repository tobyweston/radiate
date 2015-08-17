package bad.robot.radiate.teamcity

import bad.robot.radiate.Error
import bad.robot.radiate.monitor.{MonitoringTask, MonitoringTasksFactory, ThreadSafeObservable}
import bad.robot.radiate.teamcity.KnobsConfiguration._

import scalaz.\/
import scalaz.syntax.either._

/** @see [[bad.robot.radiate.monitor.MonitoringTasksFactory.multipleProjects]] */
class AllProjectsOneTaskPerProject extends ThreadSafeObservable with MonitoringTasksFactory {
  def create: Error \/ List[MonitoringTask] = {

    val server = TeamCityUrl(_)

    for {
      config    <- load orElse KnobsConfiguration.create
      url       <- config.serverUrl
      http      = HttpClientFactory().create(config)
      teamcity  = TeamCity(server(url), config.authorisation, http, new JsonProjectsUnmarshaller, new JsonProjectUnmarshaller, new JsonBuildUnmarshaller)
      all       <- teamcity.retrieveProjects
      projects  <- config.filter(all).right
      full      <- teamcity.retrieveFullProjects(projects)
      filtered  <- full.filter(_.buildTypes.nonEmpty).right
    } yield {
      filtered.map(project => new SingleProjectMonitor(project, config))
    }
  }

  override def toString = "monitoring multiple projects"
}