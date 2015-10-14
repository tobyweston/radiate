package bad.robot.radiate.teamcity

import bad.robot.radiate.Error
import bad.robot.radiate.config.{Config, KnobsConfig}
import bad.robot.radiate.monitor.{MonitoringTask, MonitoringTasksFactory, ThreadSafeObservable}
import KnobsConfig._

import scalaz.\/
import scalaz.syntax.either._

/** @see [[bad.robot.radiate.monitor.MonitoringTasksFactory.multipleProjects]] */
class AllProjectsOneTaskPerProject extends ThreadSafeObservable with MonitoringTasksFactory {
  def create: Error \/ List[MonitoringTask] = {
    for {
      file      <- load() orElse KnobsConfig.create
      config    <- Config(file)
      _         <- store(file)
      http       = HttpClientFactory().create(config)
      teamcity   = TeamCity(TeamCityUrl(config.url), config.authorisation, http, new JsonProjectsUnmarshaller, new JsonProjectUnmarshaller, new JsonBuildUnmarshaller)
      all       <- teamcity.retrieveProjects
      projects  <- all.filter(project => config.projects.contains(project.id)).right
      full      <- teamcity.retrieveFullProjects(projects)
      filtered  <- full.filter(_.buildTypes.nonEmpty).right
    } yield {
      filtered.map(project => new SingleProjectMonitor(project, config))
    }
  }

  override def toString = "monitoring multiple projects"
}