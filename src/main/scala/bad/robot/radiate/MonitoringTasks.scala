package bad.robot.radiate

import java.util.concurrent.ScheduledFuture

import bad.robot.radiate.RestartRequiredS._
import bad.robot.radiate.monitor._

class MonitoringTasksS(factory: MonitoringTasksFactoryS, monitor: MonitorS) extends Iterable[MonitoringTaskS] {

  private var tasks = List.empty[MonitoringTaskS]
  private var scheduled = List.empty[ScheduledFuture[_]]

  try {
    tasks = factory.create
  } catch {
    case e: Exception => {
      factory.notifyObservers(e)
      factory.notifyObservers(restartRequired)
    }
  } finally {
    if (tasks.isEmpty)
      factory.notifyObservers(new NothingToMonitorExceptionS)
  }

  def start() {
    scheduled = monitor.start(tasks)
  }

  def stop() {
    monitor.cancel(scheduled)
  }

  def iterator: Iterator[MonitoringTaskS] = {
    tasks.iterator
  }
}