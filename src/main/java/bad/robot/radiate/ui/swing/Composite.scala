package bad.robot.radiate.ui.swing

import bad.robot.radiate.ui.Transparency
import java.awt._

object CompositeS {

  def applyWithComposite(graphics: Graphics2D, composite: java.awt.Composite)(function: => Unit) {
    val original = graphics.getComposite
    graphics.setComposite(composite)
    try {
      function
    } finally {
      graphics.setComposite(original)
    }
  }

  /**
   * Create an {@link java.awt.AlphaComposite} from the #graphics object and apply the #transparency to it. Use so that the transparency
   * used is relative to the current transparency (ie, 50% of an already 50% transparent context).
   */
  def transparentComposite(graphics: Graphics2D, transparency: Transparency): AlphaComposite = {
    transparency.createAlphaComposite(graphics.getComposite.asInstanceOf[AlphaComposite])
  }

  def transparentComposite(transparency: Transparency): AlphaComposite = {
    transparency.createAlphaComposite
  }
}