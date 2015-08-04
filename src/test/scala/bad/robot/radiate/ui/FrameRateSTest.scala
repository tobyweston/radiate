package bad.robot.radiate.ui

import bad.robot.radiate.ui.FrameRateS._
import org.specs2.mutable.Specification

class FrameRateSTest extends Specification {

  "Frames per second as millisecond frequency" >> {
    framesPerSecond(24).asFrequencyInMillis must_== 41
    framesPerSecond(25).asFrequencyInMillis must_== 40
    framesPerSecond(30).asFrequencyInMillis must_== 33
  }

  "too high" >> {
    framesPerSecond(61) must throwA[IllegalArgumentException]
  }

  "too low" >> {
    framesPerSecond(0) must throwA[IllegalArgumentException]
  }

}
