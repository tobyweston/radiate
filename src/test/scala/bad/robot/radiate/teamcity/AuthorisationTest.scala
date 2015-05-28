package bad.robot.radiate.teamcity

import org.specs2.mutable.Specification

class AuthorisationTest extends Specification {

  "Factory creates 'Guest Authorisation' when credentials are missing" >> {
    AuthorisationS.authorisationFor(UsernameS("bob"), NoPasswordS) must_== GuestAuthorisationS
    AuthorisationS.authorisationFor(NoUsernameS, PasswordS("secret")) must_== GuestAuthorisationS
    AuthorisationS.authorisationFor(NoUsernameS, NoPasswordS) must_== GuestAuthorisationS
  }

  "Factory creates '' when credentials are supplied" >> {
    AuthorisationS.authorisationFor(UsernameS("bob"), PasswordS("secret")) must_== BasicAuthorisationS
  }
}
