package bad.robot.radiate.teamcity

import bad.robot.radiate.monitor.{ThreadSafeObservableS, MonitoringTaskS, MonitoringTasksFactoryS}

class AllProjectsAsSingleTaskS extends ThreadSafeObservableS with MonitoringTasksFactoryS {
  def create: List[MonitoringTaskS] = {
    val configuration = YmlConfigurationS.loadOrCreate(new BootstrapTeamCityS, this)
    List(new AllProjectsMonitorS(configuration))
  }

  override def toString = "multiple projects as a single aggregate"
}