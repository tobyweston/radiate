package bad.robot.radiate.config

import bad.robot.radiate.OptionSyntax._

import scalaz.{Success, Validation}

object Username {
  def validate(username: Option[String]): Validation[String, Option[Username]] = {
    Success(NonEmptyOption(username).map(value => Username(value.trim)))
  }
}

case class Username (value: String)