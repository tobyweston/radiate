package bad.robot.radiate.teamcity

import bad.robot.radiate.monitor.{ThreadSafeObservable, MonitoringTask, MonitoringTasksFactory}

class AllProjectsAsSingleTask extends ThreadSafeObservable with MonitoringTasksFactory {
  def create: List[MonitoringTask] = {
    val configuration = YmlConfiguration.loadOrCreate(new BootstrapTeamCity, this)
    List(new AllProjectsMonitor(configuration))
  }

  override def toString = "multiple projects as a single aggregate"
}