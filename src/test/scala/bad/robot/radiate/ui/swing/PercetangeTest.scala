package bad.robot.radiate.ui.swing

import bad.robot.radiate.ui.swing.Percentage._
import org.specs2.mutable.Specification

class PercetangeTest extends Specification {

  "Percentage calculations" >> {
    OneHundredPercent.of(100) must_== 100
    OneHundredPercent.of(50) must_== 50
    Percentage(68).of(50) must_== 34
  }
}
