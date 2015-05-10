package bad.robot.radiate

import java.util.Arrays.asList
import java.util.concurrent.CopyOnWriteArrayList
import bad.robot.radiate.FunctionInterfaceOps._
import bad.robot.radiate.monitor.{InformationS, ObservableS, ObserverS}

class ThreadSafeObservableS extends ObservableS {

  private val observers = new CopyOnWriteArrayList[ObserverS]

  def addObservers(observers: ObserverS*): Boolean = {
    this.observers.addAll(asList(observers:_*))
  }

  def addObservers(observers: List[ObserverS]) {
    observers.foreach(this.observers.add)
  }

  def removeObservers(observers: ObserverS*): Boolean = {
    this.observers.removeAll(asList(observers))
  }

  def removeAllObservers {
    observers.clear()
  }

  def notifyObservers(status: StatusS) {
    observers.forEach((observer: ObserverS) => observer.update(this, status))
  }

  def notifyObservers(exception: Exception) {
    observers.forEach((observer: ObserverS) =>  observer.update(this, exception))
  }

  def notifyObservers(information: InformationS) {
    observers.forEach((observer: ObserverS) =>  observer.update(this, information))
  }

  def notifyObservers(activity: ActivityS, progress: Progress) {
    observers.forEach((observer: ObserverS) =>  observer.update(this, activity, progress))
  }
}