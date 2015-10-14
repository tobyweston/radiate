package bad.robot.radiate.monitor

import scala.collection.mutable

class NonRepeatingObservable extends ThreadSafeObservable {
  private val previous = new mutable.LinkedHashSet[Information]()

  override def notifyObservers(information: Information) {
    if (previous.add(information)) {
      super.notifyObservers(information)
    }
  }
}