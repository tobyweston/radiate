package bad.robot.radiate.teamcity

import org.scalamock.specs2.MockContext
import org.specs2.mutable.Specification

class BasicAuthConfigurationSTest extends Specification {

  "Factory creates 'Guest Authentication' when no password is supplied" in new MockContext {
    val configuration = stub[TeamCityConfigurationS]

    (configuration.username _).when().returns(UsernameS("Dale"))
    (configuration.password _).when().returns(NoPasswordS)

    val auth = BasicAuthConfigurationS(configuration)
    auth must_== GuestAuthenticationS
  }

  "Factory creates 'Guest Authentication' when no username is supplied" in new MockContext {
    val configuration = stub[TeamCityConfigurationS]

    (configuration.username _).when().returns(NoUsernameS)
    (configuration.password _).when().returns(PasswordS("secret"))

    val auth = BasicAuthConfigurationS(configuration)
    auth must_== GuestAuthenticationS
  }

  "Factory creates 'Basic Authentication'" in new MockContext {
    val configuration = stub[TeamCityConfigurationS]

    (configuration.username _).when().returns(UsernameS("El Darko"))
    (configuration.password _).when().returns(PasswordS("secret"))
    (configuration.host _).when().returns("http://example.com")
    (configuration.port _).when().returns(8008)

    val auth = BasicAuthConfigurationS(configuration)
    auth must_== new BasicAuthConfigurationS(ServerS("http://example.com", 8008), UsernameS("El Darko"), PasswordS("secret"))
  }

}
