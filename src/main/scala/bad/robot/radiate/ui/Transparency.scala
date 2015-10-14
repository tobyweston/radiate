package bad.robot.radiate.ui

import java.awt.AlphaComposite.SRC_OVER
import java.awt._

object Transparency {
  var Transparent = Transparency(0.0f)
  var TwentyPercent = Transparency(0.20f)
  var SeventyFivePercent = Transparency(0.75f)
  var Opaque = Transparency(1.0f)
}

case class Transparency(percentage: Float) {

  def +(percentage: Float) = increase(percentage)

  def increase(percentage: Float): Transparency = {
    Transparency(this.percentage + percentage)
  }

  /**
   * Create a [[java.awt.AlphaComposite]] with transparency set on top of the existing composite. Set the transparency
   * relative to the current transparency (ie, 50% of the already 50% transparency).
   */
  def createAlphaComposite(current: AlphaComposite): AlphaComposite = {
    AlphaComposite.getInstance(SRC_OVER, current.getAlpha * this.percentage)
  }

  def createAlphaComposite: AlphaComposite = {
    AlphaComposite.getInstance(SRC_OVER, this.percentage)
  }
}