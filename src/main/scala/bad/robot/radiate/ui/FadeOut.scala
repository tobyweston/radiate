package bad.robot.radiate.ui

import java.beans.PropertyChangeListener

class FadeOut extends Fade {

  private val limit = 10f

  private var count = limit

  def fireEvent(listeners: List[PropertyChangeListener]) {
    count -= 1
    if (count >= 0) {
      listeners.foreach(_.propertyChange(new AlphaTransparencyChangeEvent(this, (count + 1) / limit, count / limit)))
    }
  }

  def done = count == 0

  override def toString = f"${count / limit}%.2f"
}