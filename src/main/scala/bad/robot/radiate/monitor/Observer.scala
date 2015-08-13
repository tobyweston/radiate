package bad.robot.radiate.monitor

import bad.robot.radiate._
import org.apache.log4j.Logger
import bad.robot.radiate.Error

trait Observer {
  def update(source: Observable, status: Status) { /* ignore status updates */ }
  def update(source: Observable, activity: Activity, progress: Progress) { /* ignore status updates */ }
  def update(source: Observable, information: Information) { /* ignore status updates */ }
  def update(source: Observable, exception: Exception) { /* ignore status updates */ }
  def update(source: Observable, error: Error) { /* ignore status updates */ }
}

class LoggingObserver extends Observer {
  override def update(source: Observable, exception: Exception) {
    Logger.getLogger(source.getClass).error(exception.getMessage, exception)
  }

  override def update(source: Observable, information: Information) {
    Logger.getLogger(source.getClass).info(information)
  }
}
