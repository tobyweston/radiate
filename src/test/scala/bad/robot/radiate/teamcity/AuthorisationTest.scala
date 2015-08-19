package bad.robot.radiate.teamcity

import bad.robot.radiate.config._
import org.specs2.mutable.Specification

class AuthorisationTest extends Specification {

  "Factory creates 'Guest Authorisation' when credentials are missing" >> {
   // Authorisation.authorisationFor(Username("bob"), NoPassword) must_== GuestAuthorisation
   // Authorisation.authorisationFor(NoUsername, Password("secret")) must_== GuestAuthorisation
   // Authorisation.authorisationFor(NoUsername, NoPassword) must_== GuestAuthorisation
    ko
  }

  "Factory creates '' when credentials are supplied" >> {
    // Authorisation.authorisationFor(Username("bob"), Password("secret")) must_== BasicAuthorisation
    ko
  }
}
