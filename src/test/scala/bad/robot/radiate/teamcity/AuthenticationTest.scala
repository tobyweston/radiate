package bad.robot.radiate.teamcity

import java.net.URL

import bad.robot.radiate.config._
import org.scalamock.specs2.MockContext
import org.specs2.mutable.Specification

class AuthenticationTest extends Specification {

  "Factory creates 'Guest Authentication' when no password is supplied" in new MockContext {
    val auth = Authentication(Config(null, List(), Some(Username("Dale")), None, null))
    auth must_== GuestAuthentication
  }

  "Factory creates 'Guest Authentication' when no username is supplied" in new MockContext {
    val auth = Authentication(Config(null, List(), None, Some(Password("secret")), null))
    auth must_== GuestAuthentication
  }

  "Factory creates 'Basic Authentication'" in new MockContext {
    val auth = Authentication(Config(new URL("http://example.com:8008"), List(), Some(Username("El Darko")), Some(Password("secret")), null))
    auth must_== new BasicAuthentication(new URL("http://example.com:8008"), Username("El Darko"), Password("secret"))
  }
}
