package bad.robot.radiate.config

import java.net.URL
import java.time.LocalTime

import bad.robot.radiate.{ConfigurationError, Error}

import scalaz.Scalaz._
import scalaz._

object Config {
  def apply(file: ConfigFile): Error \/ Config = {
    validate(file).leftMap(errors => ConfigurationError(errors.list.mkString(", "))).disjunction
  }

  private def validate(file: ConfigFile) = {
    (
      Url.validate(file.url).toValidationNel |@|
      Username.validate(file.username).toValidationNel |@|
      Password.validate(file.password).toValidationNel |@|
      Authorisation.validate(file.authorisation, file.username, file.password).toValidationNel |@|
      EcoMode.validate(file.ecoMode._1, file.ecoMode._2).toValidationNel
    ) { (url, username, password, authorisation, ecoMode) =>
      Config(TeamCityConfig(ServerConfig(url, username, password, authorisation), file.projects), UiConfig(ecoMode))
    }
  }
}

case class Config(teamcity: TeamCityConfig, ui: UiConfig)
case class TeamCityConfig(server: ServerConfig, projects: List[String])
case class ServerConfig(url: URL, username: Option[Username], password: Option[Password], authorisation: Authorisation)
case class UiConfig(ecoMode: Option[EcoMode])