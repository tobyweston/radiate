package bad.robot.radiate.monitor

import bad.robot.radiate.{Activity, Progress, Status}
import org.apache.log4j.Logger

trait ObserverS {
  def update(source: ObservableS, status: Status) { /* ignore status updates */ }
  def update(source: ObservableS, activity: Activity, progress: Progress) { /* ignore status updates */ }
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
