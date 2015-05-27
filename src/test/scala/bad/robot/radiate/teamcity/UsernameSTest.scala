package bad.robot.radiate.teamcity

import org.specs2.mutable.Specification

class UsernameSTest extends Specification {

  "Various examples of creating a username" >> {
    UsernameS("hello") must_== UsernameS("hello")
    UsernameS.username("hello") must_== UsernameS("hello")
    UsernameS.username(null) must_== NoUsernameS
    NoUsernameS must_== NoUsernameS
  }

}
