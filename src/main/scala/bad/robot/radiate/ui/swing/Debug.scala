package bad.robot.radiate.ui.swing

import java.awt.BasicStroke.{CAP_BUTT, JOIN_MITER}
import java.awt.Color.darkGray
import java.awt._

object DebugS {
  def drawOutlineOfRegion(region: Rectangle, graphics: Graphics2D) {
    graphics.drawRect(region.x, region.y, region.width, region.height)
  }

  def drawOutlineOfRegion(region: Rectangle, graphics: Graphics2D, color: Color) {
    val original = graphics.getColor
    graphics.setColor(color)
    graphics.setStroke(new BasicStroke(1, CAP_BUTT, JOIN_MITER, 5, Array(5f), 0.0f))
    graphics.drawRect(region.x, region.y, region.width, region.height)
    graphics.setColor(original)
  }

  def drawCentreLines(region: Rectangle, graphics: Graphics2D) {
    drawCentreLines(region, graphics, darkGray)
  }

  def drawCentreLines(region: Rectangle, graphics: Graphics2D, color: Color) {
    val original = graphics.getColor
    graphics.setColor(color)
    graphics.setStroke(new BasicStroke(1, CAP_BUTT, JOIN_MITER, 5, Array(5f), 0.0f))
    graphics.drawLine(region.x, region.y, region.width, region.height)
    graphics.drawLine(region.width, region.y, region.x, region.height)
    graphics.drawLine(region.x, region.height / 2, region.width, region.height / 2)
    graphics.drawLine(region.x + region.width / 2, region.y, region.x + region.width / 2, region.height)
    graphics.setColor(original)
  }
}