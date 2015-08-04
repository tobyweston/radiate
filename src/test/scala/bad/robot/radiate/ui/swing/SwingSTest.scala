package bad.robot.radiate.ui.swing

import java.awt.Rectangle

import bad.robot.radiate.ui.swing.RegionS._
import org.specs2.mutable.Specification

class SwingSTest extends Specification {

  "Reduced region size" >> {
    val rectangle = getReducedRegion(new Rectangle(10, 20, 200, 150), PercentageS(50))
    rectangle.x must_== 10
    rectangle.y must_== 20
    rectangle.width must_== 100
    rectangle.height must_== 75
  }

}
