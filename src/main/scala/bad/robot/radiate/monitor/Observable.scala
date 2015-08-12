package bad.robot.radiate.monitor

import bad.robot.radiate.RadiateError.Error
import bad.robot.radiate._

trait Observable {
  def addObservers(observer: List[Observer])
  def addObservers(observer: Observer*): Boolean
  def removeObservers(observer: Observer*): Boolean
  def removeAllObservers()
  def notifyObservers(status: Status)
  def notifyObservers(activity: Activity, progress: Progress)
  def notifyObservers(error: Error)
  def notifyObservers(exception: Exception)
  def notifyObservers(information: Information)
}
