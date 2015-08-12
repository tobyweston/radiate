package bad.robot.radiate

import java.util.concurrent.ScheduledFuture

import bad.robot.radiate.monitor._

class MonitoringTasksS(factory: MonitoringTasksFactory, monitor: Monitor) extends Iterable[MonitoringTask] {

  private var scheduled = List.empty[ScheduledFuture[_]]

  val tasks = factory.create.valueOr(error => {
    factory.notifyObservers(error)
    factory.notifyObservers(RestartRequired())
    List.empty[MonitoringTask]
  })
  if (tasks.isEmpty) factory.notifyObservers(new NothingToMonitorException)

  def start() {
    scheduled = monitor.start(tasks)
  }

  def stop() {
    monitor.cancel(scheduled)
  }

  def iterator: Iterator[MonitoringTask] = tasks.iterator
}