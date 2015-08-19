package bad.robot.radiate.config

import org.specs2.mutable.Specification

import scalaz.Success

class PasswordTest extends Specification {

  "Validate Password" >> {
    Password.validate(Some("hello")) must_== Success(Some(Password("hello")))
    Password.validate(Some(" hello ")) must_== Success(Some(Password("hello")))
    Password.validate(Some("")) must_== Success(None)
    Password.validate(Some("   ")) must_== Success(None)
    Password.validate(None) must_== Success(None)
  }


}
