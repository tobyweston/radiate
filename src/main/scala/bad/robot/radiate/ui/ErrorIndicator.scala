package bad.robot.radiate.ui

import java.awt.BasicStroke.{CAP_ROUND, JOIN_ROUND}
import java.awt.Color.white
import java.awt.RenderingHints.{KEY_ANTIALIASING, VALUE_ANTIALIAS_ON}
import java.awt._
import java.awt.geom.Ellipse2D
import javax.swing._
import javax.swing.plaf.LayerUI

import bad.robot.radiate.{Activity, Error}

class ErrorIndicator extends LayerUI[JComponent] {
  private var redraw: Boolean = false

  override def paint(g: Graphics, component: JComponent) {
    super.paint(g, component)
    val width = component.getWidth
    val height = component.getHeight
    if (redraw) {
      val graphics: Graphics2D = g.create.asInstanceOf[Graphics2D]
      drawErrorIndicator(width, height, graphics)
      graphics.dispose()
    }
  }

  private def drawErrorIndicator(width: Int, height: Int, graphics: Graphics2D) {
    val reductionPercentage = 20
    val size = Math.min(width, height) / reductionPercentage
    graphics.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON)
    graphics.setStroke(new BasicStroke(size / 4, CAP_ROUND, JOIN_ROUND))
    graphics.setPaint(white)
    graphics.draw(new Ellipse2D.Double(100, 100, 200, 200))
  }

  def setVisibilityBasedOn(activity: Activity) {
    if (activity == Error) redraw = true
  }
}