package bad.robot.radiate.config

import scalaz.{Failure, Success, Validation}
import bad.robot.radiate.OptionSyntax._
import scalaz.syntax.std.option._
import scalaz.Validation.FlatMap._

sealed trait Authorisation {
  val pathSegment: String
  override def toString = pathSegment
}

object Authorisation {
  def validate(authorisation: Option[String], username: Option[String], password: Option[String]): Validation[String, Authorisation] = {
    (NonEmptyOption(authorisation), NonEmptyOption(username), NonEmptyOption(password)) match {
      case (Some("guest"), _, _) => Success(GuestAuthorisation)
      case (Some("basic"), Some(_), Some(_)) => Success(BasicAuthorisation)
      case _ => Failure("The value for 'authorisation' must be either 'guest' or 'basic'. If 'basic', both 'username' and 'password' must be supplied.")
    }
  }
}

case object GuestAuthorisation extends Authorisation {
  override val pathSegment = "guestAuth"
}

case object BasicAuthorisation extends Authorisation {
  override val pathSegment = "httpAuth"
}