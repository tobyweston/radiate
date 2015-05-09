package bad.robot.radiate.monitor

import java.util.stream.Stream

import bad.robot.radiate.{Activity, Progress, Status}

trait ObservableS {
  def addObservers(observer: List[ObserverS])
  def addObservers(observer: ObserverS*): Boolean
  def removeObservers(observer: ObserverS*): Boolean
  def removeAllObservers
  def notifyObservers(status: Status)
  def notifyObservers(activity: Activity, progress: Progress)
  def notifyObservers(exception: Exception)
  def notifyObservers(information: InformationS)
}
