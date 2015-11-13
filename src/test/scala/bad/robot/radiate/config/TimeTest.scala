package bad.robot.radiate.config

import org.specs2.mutable.Specification

import scalaz.{Failure, Success}

class TimeTest extends Specification {

  implicit def tupleToTime(tuple: (String, String)) = Time(tuple._1.toInt, tuple._2.toInt)

  "Extract valid times" >> {
    Time.unapply(Some("00:00")) must_=== Some(("00", "00"))
    Time.unapply(Some("18:30")) must_=== Some(("18", "30"))
    Time.unapply(Some("23:59")) must_=== Some(("23", "59"))
    Time.unapply(Some("2:25")) must_=== Some(("2", "25"))
  }

  "Extracting invalid times" >> {
    Time.unapply(Some("0:0")) must_== None
    Time.unapply(Some("24:00")) must_== None
    Time.unapply(Some("12:1")) must_== None
    Time.unapply(Some("text")) must_== None
  }

  "Missing values" >> {
    Time.unapply(Some(null)) must_== None
    Time.unapply(Some("")) must_== None
    Time.unapply(Some(" ")) must_== None
  }

}
