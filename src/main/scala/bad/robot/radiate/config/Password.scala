package bad.robot.radiate.config

import bad.robot.radiate.OptionSyntax._

import scalaz.{Success, Validation}

object Password {

  def validate(password: Option[String]): Validation[String, Password] = {
    Success(NonEmptyOption(password).map(Password.apply).getOrElse(NoPassword))
  }

  // deprecated
  def password(password: String): Password = {
    if (password == null) NoPassword else new Password(password)
  }
}

// todo implicit to convert the SimpleHTTP password
case class Password(value: String) {
  def asSimpleHttp: bad.robot.http.configuration.Password = {
    bad.robot.http.configuration.Password.password(value)
  }
}

object NoPassword extends Password("no password")