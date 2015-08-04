package bad.robot.radiate.ui

import java.beans.PropertyChangeListener

trait Fade {
  def fireEvent(listeners: List[PropertyChangeListener])
  def done: Boolean
}
