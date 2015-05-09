package bad.robot.radiate.monitor

import bad.robot.radiate.ThreadSafeObservableS

import scala.collection.mutable

class NonRepeatingObservableS extends ThreadSafeObservableS {
  private val previous = new mutable.LinkedHashSet[InformationS]()

  override def notifyObservers(information: InformationS) {
    if (previous.add(information)) {
      super.notifyObservers(information)
    }
  }
}