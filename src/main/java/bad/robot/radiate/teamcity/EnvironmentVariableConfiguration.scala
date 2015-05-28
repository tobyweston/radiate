package bad.robot.radiate.teamcity

import bad.robot.radiate.Environment.getEnvironmentVariable
import java.lang.Integer.valueOf

class EnvironmentVariableConfigurationS extends TeamCityConfigurationS {
  def host = getEnvironmentVariable("TEAMCITY_HOST")

  def port = valueOf(getEnvironmentVariable("TEAMCITY_PORT", "8111"))

  def filter(projects: Iterable[ProjectScala]) = projects

  def password = PasswordS.password(getEnvironmentVariable("TEAMCITY_PASSWORD", null))

  def username = UsernameS.username(getEnvironmentVariable("TEAMCITY_USER", null))

  def authorisation = AuthorisationS.authorisationFor(username, password)
}