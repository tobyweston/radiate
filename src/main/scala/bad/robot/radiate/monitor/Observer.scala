package bad.robot.radiate.monitor

import bad.robot.radiate._
import org.apache.log4j.Logger

trait ObserverS {
  def update(source: ObservableS, status: StatusS) { /* ignore status updates */ }
  def update(source: ObservableS, activity: ActivityS, progress: Progress) { /* ignore status updates */ }
  def update(source: ObservableS, information: InformationS) { /* ignore status updates */ }
  def update(source: ObservableS, exception: Exception) { /* ignore status updates */ }
}

class LoggingObserverS extends ObserverS {
  override def update(source: ObservableS, exception: Exception) {
    Logger.getLogger(source.getClass).error(exception.getMessage, exception)
  }

  override def update(source: ObservableS, information: InformationS) {
    Logger.getLogger(source.getClass).info(information)
  }
}
