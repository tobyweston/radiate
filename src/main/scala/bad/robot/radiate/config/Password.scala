package bad.robot.radiate.config

import bad.robot.radiate.OptionSyntax._

import scalaz.{Success, Validation}

object Password {

  def validate(password: Option[String]): Validation[String, Password] = {
    Success(NonEmptyOption(password).map(Password.apply).getOrElse(NoPassword))
  }
}

case class Password(value: String)

object NoPassword extends Password("no password")