package bad.robot.radiate

import java.util.concurrent.ScheduledFuture

import bad.robot.radiate.RestartRequired._
import bad.robot.radiate.monitor._

class MonitoringTasksS(factory: MonitoringTasksFactory, monitor: Monitor) extends Iterable[MonitoringTask] {

  private var tasks = List.empty[MonitoringTask]
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
      factory.notifyObservers(new NothingToMonitorException)
  }

  def start() {
    scheduled = monitor.start(tasks)
  }

  def stop() {
    monitor.cancel(scheduled)
  }

  def iterator: Iterator[MonitoringTask] = {
    tasks.iterator
  }
}