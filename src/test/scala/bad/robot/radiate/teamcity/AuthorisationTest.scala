package bad.robot.radiate.teamcity

import bad.robot.radiate.config.{NoUsername, Username, NoPassword, Password}
import org.specs2.mutable.Specification

class AuthorisationTest extends Specification {

  "Factory creates 'Guest Authorisation' when credentials are missing" >> {
    Authorisation.authorisationFor(Username("bob"), NoPassword) must_== GuestAuthorisationS
    Authorisation.authorisationFor(NoUsername, Password("secret")) must_== GuestAuthorisationS
    Authorisation.authorisationFor(NoUsername, NoPassword) must_== GuestAuthorisationS
  }

  "Factory creates '' when credentials are supplied" >> {
    Authorisation.authorisationFor(Username("bob"), Password("secret")) must_== BasicAuthorisationS
  }
}
