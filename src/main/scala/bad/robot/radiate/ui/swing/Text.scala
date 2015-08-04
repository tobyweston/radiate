package bad.robot.radiate.ui.swing

import java.awt.Color._
import java.awt.Font._
import java.awt.RenderingHints._
import java.awt._
import java.awt.geom.AffineTransform._
import math._

object Text {

  def drawTextCenteredToRegion(region: Rectangle, graphics: Graphics2D, text: String) {
    setFontScaledToRegion(region, graphics, text, new Font("Arial", PLAIN, 12))
    val center = getCenterPointOfTextWithinRegion(region, graphics, graphics.getFont, text)
    graphics.setColor(white)
    graphics.drawString(text, center.x, region.y + getFontHeight(graphics, text))
  }

  private def getFontHeight(graphics: Graphics2D, text: String): Float = {
    val metrics = graphics.getFontMetrics(graphics.getFont)
    val textSize = metrics.getStringBounds(text, graphics)
    textSize.getHeight.toFloat // + metrics.getAscent());
  }

  def getCenterPointOfTextWithinRegion(region: Rectangle, graphics: Graphics2D, font: Font, text: String): Point = {
    val metrics = graphics.getFontMetrics(font)
    val textSize = metrics.getStringBounds(text, graphics)
    val x = region.x + (region.width - textSize.getWidth) / 2
    val y = region.y + (region.height - textSize.getHeight) / 2 + metrics.getAscent
    new Point(abs(x).toInt, abs(y).toInt)
  }

  def setFontScaledToRegion(region: Rectangle, graphics: Graphics2D, text: String, font: Font) {
    val metrics = graphics.getFontMetrics(font)
    val xScale: Float = (region.width / metrics.stringWidth(text)).toFloat
    val yScale: Float = region.height / metrics.getHeight
    val scale:  Float = determineWhichAxisToScaleOn(xScale, yScale)
    graphics.setFont(font.deriveFont(getScaleInstance(scale, scale)))
    graphics.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON)
  }

  private def determineWhichAxisToScaleOn(xScale: Float, yScale: Float): Float = {
    if (xScale > yScale) yScale
    else xScale
  }
}
