package bad.robot.radiate.teamcity

import org.scalamock.specs2.MockContext
import org.specs2.mutable.Specification

class AuthenticationTest extends Specification {

  "Factory creates 'Guest Authentication' when no password is supplied" in new MockContext {
    val configuration = stub[TeamCityConfiguration]

    (configuration.username _).when().returns(Username("Dale"))
    (configuration.password _).when().returns(NoPassword)

    val auth = Authentication(configuration)
    auth must_== GuestAuthenticationS
  }

  "Factory creates 'Guest Authentication' when no username is supplied" in new MockContext {
    val configuration = stub[TeamCityConfiguration]

    (configuration.username _).when().returns(NoUsername)
    (configuration.password _).when().returns(Password("secret"))

    val auth = Authentication(configuration)
    auth must_== GuestAuthenticationS
  }

  "Factory creates 'Basic Authentication'" in new MockContext {
    val configuration = stub[TeamCityConfiguration]

    (configuration.username _).when().returns(Username("El Darko"))
    (configuration.password _).when().returns(Password("secret"))
    (configuration.host _).when().returns("http://example.com")
    (configuration.port _).when().returns(8008)

    val auth = Authentication(configuration)
    auth must_== new BasicAuthentication(Server("http://example.com", 8008), Username("El Darko"), Password("secret"))
  }
}
