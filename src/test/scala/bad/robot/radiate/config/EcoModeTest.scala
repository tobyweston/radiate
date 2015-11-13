package bad.robot.radiate.config

import org.specs2.mutable.Specification

import scalaz.{Failure, Success}

class EcoModeTest extends Specification {

  "Valid times" >> {
    EcoMode.validate(Some("10:00"), Some("11:15")) must_== Success(Some(EcoMode(Time(10, 0), Time(11, 15))))
    EcoMode.validate(Some("00:00"), Some("23:59")) must_== Success(Some(EcoMode(Time(0, 0), Time(23, 59))))
  }

  val error = "The values for eco mode times must be in the range of '00:00' to '23:59'. Both 'start' and 'end' times must be supplied."

  "Invalid times" >> {
    EcoMode.validate(Some("24:00"), Some("11:00")) must_== Failure(s"$error The values was (Some(24:00),Some(11:00))")
  }

  "Missing times" >> {
    EcoMode.validate(Some("10:00"), None) must_== Failure(s"$error The values was (Some(10:00),None)")
    EcoMode.validate(None, Some("10:00")) must_== Failure(s"$error The values was (None,Some(10:00))")
    EcoMode.validate(None, None) must_== Failure(s"$error The values was (None,None)")
  }


}
