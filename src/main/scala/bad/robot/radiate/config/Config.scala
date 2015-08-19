package bad.robot.radiate.config

import java.net.URL

import bad.robot.radiate.teamcity.{Authorisation, Username}
import bad.robot.radiate.{ConfigurationError, Error}

import scalaz.Scalaz._
import scalaz._

object Config {
  def apply(internal: ConfigFile): Error \/ Config = {
    validate(internal).leftMap(errors => ConfigurationError(errors.list.mkString(", "))).disjunction
  }

  private def validate(internal: ConfigFile) = {
    (
      Url.validate(internal.url).toValidationNel |@|
      Username.validate(internal.username).toValidationNel |@|
      Password.validate(internal.password).toValidationNel
    ) { (url, username, password) =>
      Config(url, null, username, password, null)
    }
  }
}

case class Config(url: URL, projects: List[String], username: Username, password: Password, authorisation: Authorisation)

