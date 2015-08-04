package bad.robot.radiate

import org.specs2.mutable.Specification

class NullProgressTestS extends Specification {
  
  "Basic equality" >> {
    new NullProgressS() must_== new NullProgressS()
  }

  "Show the number of things progress is over" >> {
    new NullProgressS().numberOfBuilds must_== 0
  }
}