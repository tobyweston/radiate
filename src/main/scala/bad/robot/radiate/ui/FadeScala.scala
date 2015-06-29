package bad.robot.radiate.ui

import java.beans.PropertyChangeListener

trait FadeScala {
  def fireEvent(listeners: List[PropertyChangeListener])
  def done: Boolean
}
