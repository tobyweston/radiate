package bad.robot.radiate.ui

import java.awt.RenderingHints.{KEY_ANTIALIASING, VALUE_ANTIALIAS_ON}
import java.awt._
import javax.swing._
import javax.swing.plaf.basic.BasicScrollBarUI

class TransparentScrollBarUI extends BasicScrollBarUI {
  private val dimension = new Dimension

  protected override def createDecreaseButton(orientation: Int): JButton = {
    new JButton() {
      override def getPreferredSize: Dimension = dimension
    }
  }

  protected override def createIncreaseButton(orientation: Int): JButton = {
    new JButton() {
      override def getPreferredSize: Dimension = dimension
    }
  }

  protected override def paintTrack(g: Graphics, c: JComponent, r: Rectangle) {
  }

  protected override def paintThumb(g: Graphics, component: JComponent, region: Rectangle) {
    val graphics: Graphics2D = g.create.asInstanceOf[Graphics2D]
    graphics.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON)
    val scrollbar = component.asInstanceOf[JScrollBar]
    if (!scrollbar.isEnabled || region.width > region.height) {
      return
    } else if (isDragging) {
      graphics.setPaint(new Color(25, 25, 25, 251))
    } else if (isThumbRollover) {
      graphics.setPaint(new Color(30, 30, 30, 251))
    } else {
      graphics.setPaint(new Color(39, 39, 39, 251))
    }
    graphics.fillRoundRect(region.x, region.y, region.width, region.height, 10, 10)
    graphics.setPaint(Color.darkGray)
    graphics.drawRoundRect(region.x, region.y, region.width, region.height, 10, 10)
    graphics.dispose()
  }

  protected override def setThumbBounds(x: Int, y: Int, width: Int, height: Int) {
    super.setThumbBounds(x, y, width, height)
    scrollbar.repaint()
  }
}