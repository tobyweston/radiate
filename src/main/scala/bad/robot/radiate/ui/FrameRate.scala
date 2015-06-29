package bad.robot.radiate.ui

object FrameRateS {
  val videoFramesPerSecond: FrameRateS = framesPerSecond(24)

  private[ui] def framesPerSecond(framesPerSecond: Int): FrameRateS = {
    new FrameRateS(framesPerSecond)
  }
}

class FrameRateS(framesPerSecond: Int) {
  if (framesPerSecond < 1) throw new IllegalArgumentException("frame rates less than 1 seems a little low")
  if (framesPerSecond > 60) throw new IllegalArgumentException("frame rates over 60 frames per seconds seems a little high")

  private[ui] def asFrequencyInMillis: Int = 1000 / framesPerSecond
}