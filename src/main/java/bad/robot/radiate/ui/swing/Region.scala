package bad.robot.radiate.ui.swing

import java.awt._

object RegionS {
  def getReducedRegion(region: Rectangle, percentage: PercentageS): Rectangle = {
    getReducedRegion(region, percentage, percentage)
  }

  def getReducedRegion(region: Rectangle, x: PercentageS, y: PercentageS): Rectangle = {
    val width = x.of(region.width)
    val height = y.of(region.height)
    new Rectangle(region.x, region.y, width.toInt, height.toInt)
  }

  def getReducedRegionAsSquare(component: Component, percentage: PercentageS): Rectangle = {
    val region = new Rectangle(component.getX, component.getY, component.getWidth, component.getHeight)
    val width = percentage.of(Math.min(region.width, region.height))
    val height = width
    new Rectangle(region.x, region.y, width.toInt, height.toInt)
  }

  def centerRegionWithinComponent(region: Rectangle, component: Component) {
    val x = Math.abs(region.width - component.getWidth) / 2 + region.x
    var y = Math.abs(region.height - component.getHeight) / 2 + region.y
    if (y < region.y) y = region.y
    region.setLocation(x, y)
  }
  
}

object PercentageS {
  val TwentyPercent = PercentageS(20.0)
  val FiftyPercent = PercentageS(50.0)
  val EightyPercent = PercentageS(80.0)
  val NinetyPercent = PercentageS(90.0)
  val OneHundredPercent = PercentageS(100.0)
}

case class PercentageS(value: Double) {
  def of(number: Int): Double = {
    number * (value / 100)
  }
}