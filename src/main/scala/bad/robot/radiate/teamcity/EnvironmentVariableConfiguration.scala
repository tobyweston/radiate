package bad.robot.radiate.teamcity

import java.lang.Integer.valueOf

import bad.robot.radiate.EnvironmentS.getEnvironmentVariable
import bad.robot.radiate.teamcity.PasswordS.{password => teamcityPassword}
import bad.robot.radiate.teamcity.UsernameS.{username => teamcityUsername}

class EnvironmentVariableConfigurationS extends TeamCityConfigurationS {
  def host = getEnvironmentVariable("TEAMCITY_HOST")
  def port = valueOf(getEnvironmentVariable("TEAMCITY_PORT", "8111"))
  def filter(projects: Iterable[ProjectScala]) = projects
  def password = teamcityPassword(getEnvironmentVariable("TEAMCITY_PASSWORD", null))
  def username = teamcityUsername(getEnvironmentVariable("TEAMCITY_USER", null))
  def authorisation = AuthorisationS.authorisationFor(username, password)
}