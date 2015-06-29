package bad.robot.radiate.ui

import java.beans.PropertyChangeListener

class FadeInScala extends FadeScala {

  private val limit = 50f

  private var count = 0f

  def fireEvent(listeners: List[PropertyChangeListener]) {
    count += 1
    if (count <= limit) {
      listeners.foreach(_.propertyChange(new AlphaTransparencyChangeEventS(this, (count - 1) / limit, count / limit)))
    }
  }

  def done = count == limit

  override def toString = f"${count / limit}%.2f"
}