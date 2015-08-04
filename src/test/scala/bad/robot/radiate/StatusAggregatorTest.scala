package bad.robot.radiate

import bad.robot.radiate.StatusAggregator._
import org.specs2.mutable.Specification

class StatusAggregatorTest extends Specification {

  "Should be Ok" >> {
    aggregated(List(Ok, Ok, Ok)).getStatus must_== Ok
  }

  "Should be Broken" >> {
    aggregated(List(Ok, Broken, Unknown)).getStatus must_== Broken
  }

  "Should be Unknown" >> {
    aggregated(List(Ok, Ok, Unknown)).getStatus must_== Unknown
  }

  "Nothing to aggregate" >> {
    aggregated(List()).getStatus must_== Unknown
  }
}
