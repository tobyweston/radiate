package bad.robot.radiate.config

import bad.robot.radiate.OptionSyntax._

import scalaz.{Failure, Success, Validation}

sealed trait Authorisation {
  val pathSegment: String
  val name: String
  override def toString = pathSegment
}

object Authorisation {
  def validate(authorisation: Option[String], username: Option[String], password: Option[String]): Validation[String, Authorisation] = {
    (NonEmptyOption(authorisation), NonEmptyOption(username), NonEmptyOption(password)) match {
      case (Some("guest"), _, _) => Success(GuestAuthorisation)
      case (Some("basic"), Some(_), Some(_)) => Success(BasicAuthorisation)
      case value @ _ => Failure(s"The value for 'authorisation' must be either 'guest' or 'basic'. If 'basic', both 'username' and 'password' must be supplied. The value was $value")
    }
  }

  def validate(username: Option[_], password: Option[_]): Validation[String, Authorisation] = (username, password) match {
    case (Some(_), Some(_)) => Success(BasicAuthorisation)
    case _ => Success(GuestAuthorisation)
  }
}

case object GuestAuthorisation extends Authorisation {
  val pathSegment = "guestAuth"
  val name = "guest"
}

case object BasicAuthorisation extends Authorisation {
  val pathSegment = "httpAuth"
  val name = "basic"
}