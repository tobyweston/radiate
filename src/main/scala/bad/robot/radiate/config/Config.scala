package bad.robot.radiate.config

import java.net.URL

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
      Authorisation.validate(file.authorisation, file.username, file.password).toValidationNel
    ) { (url, username, password, authorisation) =>
      Config(url, file.projects, username, password, authorisation)
    }
  }
}

case class Config(url: URL, projects: List[String], username: Option[Username], password: Option[Password], authorisation: Authorisation)

