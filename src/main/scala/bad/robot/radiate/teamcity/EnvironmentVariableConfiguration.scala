package bad.robot.radiate.teamcity

import java.lang.Integer.valueOf

import bad.robot.radiate.ConfigurationError
import bad.robot.radiate.Environment.getEnvironmentVariable
import bad.robot.radiate.UrlValidator._
import bad.robot.radiate.teamcity.Password.{password => teamcityPassword}
import bad.robot.radiate.teamcity.Username.{username => teamcityUsername}

import scalaz.syntax.std.option._

class EnvironmentVariableConfiguration extends TeamCityConfiguration {
  def server = {
    val server = getEnvironmentVariable("TEAMCITY_SERVER")
    for {
      string <- server.toRightDisjunction(ConfigurationError("Please set environment variable 'TEAMCITY_HOST'"))
      url    <- validate(string).leftMap(cause => ConfigurationError(s"Invalid environment variable for 'TEAMCITY_HOST'. $cause"))
    } yield url
  }
  def host = getEnvironmentVariable("TEAMCITY_HOST")
  def port = valueOf(getEnvironmentVariable("TEAMCITY_PORT", "8111"))
  def filter(projects: Iterable[Project]) = projects
  def password = teamcityPassword(getEnvironmentVariable("TEAMCITY_PASSWORD", null))
  def username = teamcityUsername(getEnvironmentVariable("TEAMCITY_USER", null))
  def authorisation = Authorisation.authorisationFor(username, password)
}