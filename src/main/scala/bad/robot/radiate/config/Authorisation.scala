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
    NonEmptyOption(authorisation) match {
      case Some("guest") => Success(GuestAuthorisation)
      case Some("basic") => validate(username, password)
      case _ => Failure("'authorisation' must be either 'guest' or 'basic'")
    }
  }

  private def validate(username: Option[String], password: Option[String]): Validation[String, Authorisation] = {
    for {
      user <- Username.validate(username)
      pass <- Password.validate(password)
    } yield {
      if ((user == NoUsername) || (pass == NoPassword)) GuestAuthorisation
      else BasicAuthorisation
    }
  }
}

case object GuestAuthorisation extends Authorisation {
  override val pathSegment = "guestAuth"
}

case object BasicAuthorisation extends Authorisation {
  override val pathSegment = "httpAuth"
}