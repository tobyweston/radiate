package bad.robot.radiate.teamcity

import java.net.URL

import bad.robot.radiate.config.Config
import org.scalamock.specs2.MockContext
import org.specs2.mutable.Specification

import scalaz.\/-

class AuthenticationTest extends Specification {

  "Factory creates 'Guest Authentication' when no password is supplied" in new MockContext {
    val auth = Authentication(Config(null, List(), Username("Dale"), NoPassword, null))
    auth must_== GuestAuthenticationS
  }

  "Factory creates 'Guest Authentication' when no username is supplied" in new MockContext {
    val auth = Authentication(Config(null, List(), NoUsername, Password("secret"), null))
    auth must_== GuestAuthenticationS
  }

  "Factory creates 'Basic Authentication'" in new MockContext {
    val auth = Authentication(Config(new URL("http://example.com:8008"), List(), Username("El Darko"), Password("secret"), null))
    auth must_== new BasicAuthentication(new URL("http://example.com:8008"), Username("El Darko"), Password("secret"))
  }
}
