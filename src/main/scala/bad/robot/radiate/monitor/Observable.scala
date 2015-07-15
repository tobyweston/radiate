package bad.robot.radiate.monitor

import bad.robot.radiate._

trait ObservableS {
  def addObservers(observer: List[ObserverS])
  def addObservers(observer: ObserverS*): Boolean
  def removeObservers(observer: ObserverS*): Boolean
  def removeAllObservers()
  def notifyObservers(status: StatusS)
  def notifyObservers(activity: ActivityS, progress: ProgressS)
  def notifyObservers(exception: Exception)
  def notifyObservers(information: InformationS)
}
