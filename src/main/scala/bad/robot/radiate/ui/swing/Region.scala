package bad.robot.radiate.ui.swing

import java.awt.{Component, Rectangle}
import math._

object Region {

  def getReducedRegion(region: Rectangle, percentage: Percentage): Rectangle = {
    getReducedRegion(region, percentage, percentage)
  }

  def getReducedRegion(region: Rectangle, x: Percentage, y: Percentage): Rectangle = {
    val width = x.of(region.width)
    val height = y.of(region.height)
    new Rectangle(region.x, region.y, width.toInt, height.toInt)
  }

  def getReducedRegionAsSquare(component: Component, percentage: Percentage): Rectangle = {
    val region = new Rectangle(component.getX, component.getY, component.getWidth, component.getHeight)
    val width = percentage.of(min(region.width, region.height))
    val height = width
    new Rectangle(region.x, region.y, width.toInt, height.toInt)
  }

  def centerRegionWithinComponent(region: Rectangle, component: Component) {
    val x = abs(region.width - component.getWidth) / 2 + region.x
    var y = abs(region.height - component.getHeight) / 2 + region.y
    if (y < region.y) y = region.y
    region.setLocation(x, y)
  }

}
