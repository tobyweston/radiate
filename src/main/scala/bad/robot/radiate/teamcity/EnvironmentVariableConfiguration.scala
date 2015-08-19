package bad.robot.radiate.teamcity

import bad.robot.radiate.ConfigurationError
import bad.robot.radiate.Environment.getEnvironmentVariable
import bad.robot.radiate.UrlValidator._
import bad.robot.radiate.config._
import EnvironmentVariableConfiguration._

import scalaz.syntax.std.option._

object EnvironmentVariableConfiguration {
  // temp
  def authorisationFor(username: Username, password: Password): Authorisation = {
    if ((username == NoUsername) || (password == NoPassword))
      GuestAuthorisation
    else
      BasicAuthorisation
  }

  def tcUsername(username: String): Username = {
    if (username == null) NoUsername else new Username(username)
  }

  def tcPassword(password: String): Password = {
    if (password == null) NoPassword else new Password(password)
  }

}

class EnvironmentVariableConfiguration extends DeprecatedConfig {
  def serverUrl = {
    val server = getEnvironmentVariable("TEAMCITY_SERVER")
    for {
      string <- server.toRightDisjunction(ConfigurationError("Please set environment variable 'TEAMCITY_SERVER'"))
      url    <- validate(string).leftMap(cause => ConfigurationError(s"Invalid environment variable for 'TEAMCITY_SERVER'. $cause"))
    } yield url
  }
  def filter(projects: Iterable[Project]) = projects
  def password = tcPassword(getEnvironmentVariable("TEAMCITY_PASSWORD", null))
  def username = tcUsername(getEnvironmentVariable("TEAMCITY_USER", null))
  def authorisation = authorisationFor(username, password)
}