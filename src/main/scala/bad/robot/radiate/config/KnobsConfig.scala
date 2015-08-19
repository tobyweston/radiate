package bad.robot.radiate.config

import java.io.File
import java.net.URL

import bad.robot.radiate.Environment._
import bad.robot.radiate.{ConfigurationError, Error, UrlValidator}
import knobs._

import scalaz.\/

object KnobsConfig {

  val file = new File(sys.props("user.home") + File.separator + ".radiate" + File.separator + "radiate.cfg")

  def load(resource: KnobsResource = Required(FileResource(file))): Error \/ ConfigFile = {
    val config = knobs.loadImmutable(resource :: Nil).attemptRun
    config.leftMap(error => ConfigurationError(error.getMessage)).map(config => new ConfigFile {
      def url = config.lookup[String]("server.url")
      def projects = config.lookup[List[String]]("projects").getOrElse(List())
      def username = config.lookup[String]("server.username")
      def password = config.lookup[String]("server.password")
      def authorisation = config.lookup[String]("server.authorisation")
    })
  }

  def create: Error \/ ConfigFile = {
    for {
      url <- Url.validate(getEnvironmentVariable("TEAMCITY_URL")).leftMap(cause => ConfigurationError(s"Invalid environment variable for 'TEAMCITY_URL'. $cause"))
    } yield url
    ???
  }

  implicit val configuredUrl: Configured[URL] = new Configured[URL] {
    def apply(value: CfgValue) = value match {
      case CfgText(url) => UrlValidator.validate(url).toOption
      case _ => None
    }
  }

}
