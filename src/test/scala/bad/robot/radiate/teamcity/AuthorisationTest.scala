package bad.robot.radiate.teamcity

import bad.robot.radiate.config._
import org.specs2.mutable.Specification

import scalaz.Success
import scalaz.Failure

class AuthorisationTest extends Specification {

  "Creates 'Guest Authorisation' when credentials are missing" >> {
    Authorisation.validate(Some(Username("bob")), None) must_== Success(GuestAuthorisation)
    Authorisation.validate(None, Some(Password("secret"))) must_== Success(GuestAuthorisation)
    Authorisation.validate(None, None) must_== Success(GuestAuthorisation)
  }

  "Creates 'Basic Authorisation' when credentials are supplied" >> {
     Authorisation.validate(Some(Username("bob")), Some(Password("secret"))) must_== Success(BasicAuthorisation)
  }

  "When authorisation method is set, ensure the username and password are consistent" >> {
    "For 'guest' auth" >> {
      Authorisation.validate(Some("guest"), Some("username"), Some("password")) must_== Success(GuestAuthorisation)
      Authorisation.validate(Some("guest"), None, Some("password")) must_== Success(GuestAuthorisation)
      Authorisation.validate(Some("guest"), Some("username"), None) must_== Success(GuestAuthorisation)
      Authorisation.validate(Some("guest"), None, None) must_== Success(GuestAuthorisation)
      Authorisation.validate(Some("guest"), Some("  "), None) must_== Success(GuestAuthorisation)
      Authorisation.validate(Some("guest"), None, Some("  ")) must_== Success(GuestAuthorisation)
    }

    "For 'basic' auth" >> {
      val error = "The value for 'authorisation' must be either 'guest' or 'basic'. If 'basic', both 'username' and 'password' must be supplied. The value was "
      Authorisation.validate(Some("basic"), Some("username"), Some("password")) must_== Success(BasicAuthorisation)
      Authorisation.validate(Some("basic"), Some("username"), Some("  ")) must_== Failure(error + "(Some(basic),Some(username),None)")
      Authorisation.validate(Some("basic"), Some("username"), None) must_== Failure(error + "(Some(basic),Some(username),None)")
      Authorisation.validate(Some("basic"), None, Some("password")) must_== Failure(error + "(Some(basic),None,Some(password))")
      Authorisation.validate(Some("basic"), None, None) must_== Failure(error + "(Some(basic),None,None)")
    }

    "For invalid auth types" >> {
      val error = "The value for 'authorisation' must be either 'guest' or 'basic'. If 'basic', both 'username' and 'password' must be supplied. The value was "
      Authorisation.validate(Some("BASIC"), Some("username"), Some("password")) must_== Failure(error + "(Some(BASIC),Some(username),Some(password))")
      Authorisation.validate(Some(""), Some("username"), Some("password")) must_== Failure(error + "(None,Some(username),Some(password))")
      Authorisation.validate(Some(" basic "), Some("username"), Some("password")) must_== Failure(error + "(Some( basic ),Some(username),Some(password))")
    }
  }
}
