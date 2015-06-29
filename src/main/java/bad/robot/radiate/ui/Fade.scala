package bad.robot.radiate.ui

import java.beans.PropertyChangeListener

trait FadeScala {
  def fireEvent(listeners: Array[PropertyChangeListener])
  def done: Boolean
}