package bad.robot.radiate

import bad.robot.radiate.OptionSyntax._

import scalaz.syntax.std.option._

object Environment {

  def getEnvironmentVariable(variable: String): Option[String] = {
    NonEmptyOption(sys.env.get(variable))
  }

  def getEnvironmentVariable(variable: String, defaultValue: String): String = {
    NonEmptyOption(sys.env.get(variable)).some(identity).none(defaultValue)
  }
}
