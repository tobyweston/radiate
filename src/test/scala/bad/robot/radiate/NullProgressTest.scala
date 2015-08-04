package bad.robot.radiate

import org.specs2.mutable.Specification

class NullProgressTestS extends Specification {
  
  "Basic equality" >> {
    new NullProgress() must_== new NullProgress()
  }

  "Show the number of things progress is over" >> {
    new NullProgress().numberOfBuilds must_== 0
  }
}