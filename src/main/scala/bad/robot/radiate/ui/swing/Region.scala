package bad.robot.radiate.ui.swing

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