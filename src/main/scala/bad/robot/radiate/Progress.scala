package bad.robot.radiate

case class Progress(private var current: Int, private var max: Double) {

  private var _numberOfBuilds = 1

  private def this(current: Int, max: Int, numberOfBuilds: Int) = {
    this(current, max)
    this._numberOfBuilds = numberOfBuilds
  }

  def increment() {
    current += 1
  }

  def decrement() {
    current -= 1
  }

  def add(progress: Progress): Progress = {
    new Progress(current + progress.current, (max + progress.max).toInt, numberOfBuilds(progress))
  }

  private def numberOfBuilds(progress: Progress): Int = {
    numberOfBuilds + progress.numberOfBuilds
  }

  def asAngle: Int = {
    -asPercentageOf(360).intValue
  }

  private def asPercentage: Int = {
    asPercentageOf(100).intValue
  }

  private def asPercentageOf(i: Int): Double = {
    current / max * i
  }

  def complete: Boolean = {
    current >= max
  }

  def numberOfBuilds: Int = {
    _numberOfBuilds
  }

  def <(progress: Progress): Boolean = {
    asPercentage < progress.asPercentage
  }

  def >(progress: Progress): Boolean = {
    asPercentage > progress.asPercentage
  }

  override def toString: String = {
    asPercentage + "%"
  }
}
