package bad.robot.radiate.teamcity

import org.specs2.mutable.Specification

class UsernameTest extends Specification {

  "Various examples of creating a username" >> {
    Username("hello") must_== Username("hello")
    Username.username("hello") must_== Username("hello")
    Username.username(null) must_== NoUsername
    NoUsername must_== NoUsername
  }

}