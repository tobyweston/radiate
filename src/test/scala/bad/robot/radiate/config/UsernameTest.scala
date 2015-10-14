package bad.robot.radiate.config

import org.specs2.mutable.Specification

import scalaz.Success

class UsernameTest extends Specification {

  "Validate username" >> {
    Username.validate(Some("hello")) must_== Success(Some(Username("hello")))
    Username.validate(Some(" hello ")) must_== Success(Some(Username("hello")))
    Username.validate(Some("")) must_== Success(None)
    Username.validate(Some("   ")) must_== Success(None)
    Username.validate(None) must_== Success(None)
  }

}
