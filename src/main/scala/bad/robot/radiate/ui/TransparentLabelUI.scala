package bad.robot.radiate.ui

import java.awt.AlphaComposite.{SRC_OVER, getInstance}
import java.awt.Font.BOLD
import java.awt.RenderingHints.{KEY_ANTIALIASING, VALUE_ANTIALIAS_ON}
import java.awt._
import javax.swing._
import javax.swing.plaf.basic.BasicLabelUI

object TransparentLabelUIS {
  def initHudComponent(component: JComponent, isDarkColorScheme: Boolean) {
    val font = UIManager.getFont("Button.font").deriveFont(BOLD, 11.0f)
    component.setFont(font)
    if (isDarkColorScheme)
      component.setForeground(Color.white)
    else
      component.setForeground(Color.black)
    component.setOpaque(false)
  }

  def updateGraphicsToPaintDisabledControlIfNecessary(graphics: Graphics2D, component: Component) {
    if (!component.isEnabled)
      graphics.setComposite(getInstance(SRC_OVER, 0.5f))
  }
}

class TransparentLabelUIS extends BasicLabelUI {
  private val isDarkColorScheme = true

  protected override def installDefaults(label: JLabel) {
    super.installDefaults(label)
    TransparentLabelUIS.initHudComponent(label, isDarkColorScheme)
  }

  override def paint(graphics: Graphics, component: JComponent) {
    TransparentLabelUIS.updateGraphicsToPaintDisabledControlIfNecessary(graphics.asInstanceOf[Graphics2D], component)
    graphics.asInstanceOf[Graphics2D].setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON)
    super.paint(graphics, component)
  }

  protected override def paintDisabledText(label: JLabel, graphics: Graphics, string: String, textX: Int, textY: Int) {
    super.paintEnabledText(label, graphics, string, textX, textY)
  }
}