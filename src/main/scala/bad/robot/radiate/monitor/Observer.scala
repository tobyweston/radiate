package bad.robot.radiate.monitor

import bad.robot.radiate._
import org.apache.log4j.Logger
import bad.robot.radiate.Error

trait Observer {
  def update(source: Observable, status: Status)
  def update(source: Observable, activity: Activity, progress: Progress)
  def update(source: Observable, information: Information)
  def update(source: Observable, exception: Exception)
  def update(source: Observable, error: Error)
}

class LoggingObserver extends Observer {
  override def update(source: Observable, status: Status): Unit = () /** ignore status updates **/

  override def update(source: Observable, activity: Activity, progress: Progress): Unit = () /** ignore progress updates **/

  override def update(source: Observable, information: Information) {
    Logger.getLogger(source.getClass).info(information)
  }

  override def update(source: Observable, exception: Exception) {
    Logger.getLogger(source.getClass).error(exception.getMessage, exception)
  }

  override def update(source: Observable, error: Error) {
    Logger.getLogger(source.getClass).error(error.message)
  }
}
