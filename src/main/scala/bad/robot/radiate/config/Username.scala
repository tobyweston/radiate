package bad.robot.radiate.config

import bad.robot.radiate.OptionSyntax._

import scalaz.{Success, Validation}

object Username {
  def validate(username: Option[String]): Validation[String, Username] = {
    Success(NonEmptyOption(username).map(Username.apply).getOrElse(NoUsername))
  }
}

case class Username (value: String) 

object NoUsername extends Username("no username")