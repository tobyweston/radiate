package bad.robot.radiate.teamcity

import bad.robot.radiate.ConfigurationError
import bad.robot.radiate.Environment.getEnvironmentVariable
import bad.robot.radiate.UrlValidator._
import bad.robot.radiate.teamcity.Password.{password => teamcityPassword}
import bad.robot.radiate.teamcity.Username.{username => teamcityUsername}

import scalaz.syntax.std.option._

class EnvironmentVariableConfiguration extends DeprecatedConfig {
  def serverUrl = {
    val server = getEnvironmentVariable("TEAMCITY_SERVER")
    for {
      string <- server.toRightDisjunction(ConfigurationError("Please set environment variable 'TEAMCITY_SERVER'"))
      url    <- validate(string).leftMap(cause => ConfigurationError(s"Invalid environment variable for 'TEAMCITY_SERVER'. $cause"))
    } yield url
  }
  def filter(projects: Iterable[Project]) = projects
  def password = teamcityPassword(getEnvironmentVariable("TEAMCITY_PASSWORD", null))
  def username = teamcityUsername(getEnvironmentVariable("TEAMCITY_USER", null))
  def authorisation = Authorisation.authorisationFor(username, password)
}