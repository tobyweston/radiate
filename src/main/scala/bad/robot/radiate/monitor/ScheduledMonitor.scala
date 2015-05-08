package bad.robot.radiate.monitor

import java.util.concurrent.TimeUnit.SECONDS
import java.util.concurrent.{ScheduledExecutorService, ScheduledFuture}

import scala.collection.JavaConverters._

class ScheduledMonitorS(executor: ScheduledExecutorService) extends MonitorS {

  private val frequency: Int = 30

  def start(tasks: List[MonitoringTask]): List[ScheduledFuture[_]] = {
    tasks.map(task => executor.scheduleWithFixedDelay(task, 0, frequency, SECONDS))
  }

  def cancel(tasks: List[ScheduledFuture[_]]) {
    tasks.foreach(task => task.cancel(true))
  }

  def stop: List[Runnable] = {
    val tasks = executor.shutdownNow.asScala
    tasks.foreach(task => task.asInstanceOf[ScheduledFuture[_]].cancel(true))
    tasks.toList
  }

}