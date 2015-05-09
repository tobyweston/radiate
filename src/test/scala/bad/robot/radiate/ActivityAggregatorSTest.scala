package bad.robot.radiate

import bad.robot.radiate.ActivityAggregatorS._
import bad.robot.radiate.ActivityS._
import org.specs2.mutable.Specification

class ActivityAggregatorSTest extends Specification {

  "Empty list" >> {
    aggregated(List()).getActivity must_== Idle
  }

  "Should be Idle" >> {
    aggregated(List(Idle)).getActivity must_== Idle
    aggregated(List(Idle, Idle)).getActivity must_== Idle
  }

  "Should be Progressing" >> {
    aggregated(List(Progressing)).getActivity must_== Progressing
    aggregated(List(Idle, Progressing)).getActivity must_== Progressing
    aggregated(List(Idle, Progressing, Idle)).getActivity must_== Progressing
  }

  "Should be Busy" >> {
    aggregated(List(Busy)).getActivity must_== Busy
    aggregated(List(Busy, Idle, Progressing)).getActivity must_== Busy
    aggregated(List(Busy, Idle, Progressing, Progressing, Idle)).getActivity must_== Busy
  }

  "Should be indicating an Error" >> {
    aggregated(List(Error)).getActivity must_== Error
    aggregated(List(Busy, Idle, Error, Progressing)).getActivity must_== Error
    aggregated(List(Idle, Idle, Progressing, Busy, Error, Idle)).getActivity must_== Error
  }
}


