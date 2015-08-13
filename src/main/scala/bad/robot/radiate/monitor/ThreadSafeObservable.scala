package bad.robot.radiate.monitor

import java.util.Arrays.asList
import java.util.concurrent.CopyOnWriteArrayList

import bad.robot.radiate.FunctionInterfaceOps._
import bad.robot.radiate.{Activity, Progress, Status}
import bad.robot.radiate.Error

class ThreadSafeObservable extends Observable {

  private val observers = new CopyOnWriteArrayList[Observer]

  def addObservers(observers: Observer*): Boolean = {
    this.observers.addAll(asList(observers:_*))
  }

  def addObservers(observers: List[Observer]) {
    observers.foreach(this.observers.add)
  }

  def removeObservers(observers: Observer*): Boolean = {
    this.observers.removeAll(asList(observers))
  }

  def removeAllObservers() {
    observers.clear()
  }

  def notifyObservers(status: Status) {
    observers.forEach((observer: Observer) => observer.update(this, status))
  }

  def notifyObservers(exception: Exception) {
    observers.forEach((observer: Observer) =>  observer.update(this, exception))
  }

  def notifyObservers(error: Error) {
    observers.forEach((observer: Observer) =>  observer.update(this, error))
  }

  def notifyObservers(information: Information) {
    observers.forEach((observer: Observer) =>  observer.update(this, information))
  }

  def notifyObservers(activity: Activity, progress: Progress) {
    observers.forEach((observer: Observer) =>  observer.update(this, activity, progress))
  }
}