package bad.robot.radiate.config

import java.io.File
import java.net.URL

import bad.robot.radiate.Environment._
import bad.robot.radiate.teamcity._
import bad.robot.radiate.{ConfigurationError, Error, UrlValidator}
import knobs._

import scalaz.{Validation, \/, Failure, Success}
import scalaz.Validation.FlatMap._

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

    val bootstrap = for {
      _url            <- Url.validate(getEnvironmentVariable("TEAMCITY_URL")).leftMap(cause => ConfigurationError(s"Invalid environment variable for 'TEAMCITY_URL'. $cause"))
      _username       <- Username.validate(getEnvironmentVariable("TEAMCITY_USERNAME")).leftMap(cause => ConfigurationError(s"Not expecting to see this, no username is still valid. $cause"))
      _password       <- Password.validate(getEnvironmentVariable("TEAMCITY_PASSWORD")).leftMap(cause => ConfigurationError(s"Not expecting to see this, no password is still valid. $cause"))
      _authorisation  <- Authorisation.validate(_username, _password).leftMap(cause => ConfigurationError(s"Not expecting to see this, authorisation is derived from username and password. $cause"))
      config          = Config(_url, null, _username, _password, _authorisation)
      http            = HttpClientFactory().create(config)
      teamcity        = TeamCity(TeamCityUrl(_url), _authorisation, http, new JsonProjectsUnmarshaller, new JsonProjectUnmarshaller, new JsonBuildUnmarshaller)
      projects        <- teamcity.retrieveProjects.validation
    } yield (config, projects.map(_.id))

    bootstrap.map {
      case (config, _projects) => new ConfigFile {
        def url = Some(config.url.toExternalForm)
        def projects = _projects.toList
        def username = config.username.map(_.value)
        def password = config.password.map(_.value)
        def authorisation = Some(config.authorisation.name)
      }
    }.disjunction
  }

  implicit val configuredUrl: Configured[URL] = new Configured[URL] {
    def apply(value: CfgValue) = value match {
      case CfgText(url) => UrlValidator.validate(url).toOption
      case _ => None
    }
  }

}
