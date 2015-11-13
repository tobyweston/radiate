package bad.robot.radiate.config

import java.io.{File, PrintWriter}
import java.net.URL

import bad.robot.radiate.Environment._
import bad.robot.radiate.UrlSyntax.stringToUrl
import bad.robot.radiate.teamcity._
import bad.robot.radiate.{ConfigurationError, Error, UrlValidator}
import knobs._

import scalaz.Validation.FlatMap._
import scalaz._

object KnobsConfig {

  val file = new File(sys.props("user.home") + File.separator + ".radiate" + File.separator + "radiate.cfg")

  def load(resource: KnobsResource = Required(FileResource(file))): Error \/ ConfigFile = {
    val config = knobs.loadImmutable(resource :: Nil).attemptRun
    config.leftMap(error => ConfigurationError(s"There was an error loading config from ${file.getAbsolutePath};\n${error.getMessage}")).map(config => new ConfigFile {
      def url = config.lookup[String]("server.url")
      def projects = config.lookup[List[String]]("projects").getOrElse(List())
      def username = config.lookup[String]("server.username")
      def password = config.lookup[String]("server.password")
      def authorisation = config.lookup[String]("server.authorisation")
      def ecoMode = (config.lookup[String]("ui.eco-mode.start"), config.lookup[String]("ui.eco-mode.end"))
    })
  }

  def create: Error \/ ConfigFile = {
    create(getEnvironmentVariable)
  }

  def create(getEnvironmentVariable: (String) => Option[String]): Error \/ ConfigFile = {
    val bootstrap = for {
      _url            <- Url.validate(getEnvironmentVariable("TEAMCITY_URL")).leftMap(cause => ConfigurationError(s"Invalid environment variable for 'TEAMCITY_URL'. $cause"))
      _username       <- Username.validate(getEnvironmentVariable("TEAMCITY_USERNAME")).leftMap(cause => ConfigurationError(s"Not expecting to see this, no username is still valid. $cause"))
      _password       <- Password.validate(getEnvironmentVariable("TEAMCITY_PASSWORD")).leftMap(cause => ConfigurationError(s"Not expecting to see this, no password is still valid. $cause"))
      _authorisation  <- Authorisation.validate(_username, _password).leftMap(cause => ConfigurationError(s"Not expecting to see this, authorisation is derived from username and password. $cause"))
      config          = Config(TeamCityConfig(ServerConfig(_url, _username, _password, _authorisation), null), null)
      http            = HttpClientFactory().create(config)
      teamcity        = TeamCity(TeamCityUrl(_url), _authorisation, http, new JsonProjectsUnmarshaller, new JsonProjectUnmarshaller, new JsonBuildUnmarshaller)
      projects        <- teamcity.retrieveProjects.validation
    } yield (config, projects.map(_.id))

    bootstrap.map {
      case (config, _projects) => new ConfigFile {
        def url = Some(config.teamcity.server.url.toExternalForm)
        def projects = _projects.toList
        def username = config.teamcity.server.username.map(_.value)
        def password = config.teamcity.server.password.map(_.value)
        def authorisation = Some(config.teamcity.server.authorisation.name)
        def ecoMode = (Some("18:00"), Some("07:00"))
      }
    }.disjunction
  }

  def store(config: ConfigFile): Error \/ Boolean = {
    if (!file.exists() || file.length() == 0) {
      val writer = new PrintWriter(file)
      val written = \/.fromTryCatchNonFatal(writer.write(Template(config)))
        .leftMap(error => ConfigurationError(s"Failed to create new config file ${file.getAbsolutePath}; ${error.getMessage}"))
        .map(_ => true)
      writer.close()
      written
    } else {
      \/-(false)
    }
  }
}

object Template {
  def apply(config: ConfigFile): String = {
    def url = config.url.map(value => "url = \"" + value.toExternalForm + "\"").getOrElse("# url = \"http://example.com:8111\"")
    def username = config.username.map(value => "username = \"" + value + "\"").getOrElse("# username = \"???\"")
    def password = config.password.map(value => "password = \"" + value + "\"").getOrElse("# password = \"???\"")
    def authorisation = config.authorisation.map(value => "authorisation = \"" + value + "\"   # guest | basic").getOrElse("# authorisation = \"guest | basic\"")
    def eco: Option[String] = for {
      start   <- config.ecoMode._1
      end     <- config.ecoMode._2
      result  <- Some(s"""eco-mode {
                         |        start = "$start"
                         |        end = "$end"
                         |    }""".stripMargin)
    } yield result
    val defaultEcoMode = s"""eco-mode {
                            |        # supply *both* start and end time to enable eco mode
                            |        # start = "18:00"  # Uncomment to enable eco mode start time (hh:mm)
                            |        # end = "07:00"    # Uncomment to enable eco mode end time (hh:mm)
                            |    }""".stripMargin

    s"""
      |server {
      |    $url
      |    $username
      |    $password
      |    $authorisation
      |}
      |
      |projects = ${if (config.projects.isEmpty) "[ ]" else config.projects.mkString("[\"", "\", \"", "\"]")}
      |
      |ui {
      |    ${eco.getOrElse(s"$defaultEcoMode")}
      |}
      |""".stripMargin
  }
}
