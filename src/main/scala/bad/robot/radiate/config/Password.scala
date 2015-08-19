package bad.robot.radiate.config

import bad.robot.radiate.OptionSyntax._

import scalaz.{Success, Validation}

object Password {

  def validate(password: Option[String]): Validation[String, Option[Password]] = {
    Success(NonEmptyOption(password).map(value => Password(value.trim)))
  }
}

case class Password(value: String)