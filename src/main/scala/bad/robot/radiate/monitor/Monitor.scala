package bad.robot.radiate.monitor

import java.util.concurrent.ScheduledFuture

trait MonitorS {
  def start(tasks: List[MonitoringTask]): List[ScheduledFuture[_]]
  def cancel(tasks: List[ScheduledFuture[_]])
  def stop: List[Runnable]
}
