package bad.robot.radiate.teamcity

import bad.robot.radiate.monitor.{ThreadSafeObservable, MonitoringTask, MonitoringTasksFactory}
import bad.robot.radiate.Error
import scalaz.\/
import scalaz.syntax.either._

class AllProjectsAsSingleTask extends ThreadSafeObservable with MonitoringTasksFactory {
  def create: Error \/ List[MonitoringTask] = {
    val configuration = YmlConfiguration.loadOrCreate(new BootstrapTeamCity, this)
    List(new AllProjectsMonitor(configuration)).right
  }

  override def toString = "multiple projects as a single aggregate"
}