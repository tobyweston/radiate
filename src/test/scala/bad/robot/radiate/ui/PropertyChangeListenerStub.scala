package bad.robot.radiate.ui

import java.beans.{PropertyChangeEvent, PropertyChangeListener}

class PropertyChangeListenerScalaStub extends PropertyChangeListener {

  private val results = new scala.collection.mutable.MutableList[String]

  def propertyChange(event: PropertyChangeEvent) {
    results += event.toString
  }

  def contains(result: String) = {
    results.contains(result)
  }

  def size = results.size
}