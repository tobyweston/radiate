package bad.robot.radiate.ui.swing

import bad.robot.radiate.ui.swing.PercentageS._
import org.specs2.mutable.Specification

class PercetangeSTest extends Specification {

  "Percentage calculations" >> {
    OneHundredPercent.of(100) must_== 100
    OneHundredPercent.of(50) must_== 50
    PercentageS(68).of(50) must_== 34
  }
}
