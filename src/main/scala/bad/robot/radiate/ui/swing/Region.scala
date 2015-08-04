package bad.robot.radiate.ui.swing

object Percentage {
  val TwentyPercent = Percentage(20.0)
  val FiftyPercent = Percentage(50.0)
  val EightyPercent = Percentage(80.0)
  val NinetyPercent = Percentage(90.0)
  val OneHundredPercent = Percentage(100.0)
}

case class Percentage(value: Double) {
  def of(number: Int): Double = {
    number * (value / 100)
  }
}