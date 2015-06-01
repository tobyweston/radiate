package bad.robot.radiate.teamcity

import java.io.{File, FileNotFoundException, FileReader}

import bad.robot.radiate.monitor.{InformationS, ObservableS}
import bad.robot.radiate.teamcity.AuthorisationS.authorisationFor
import org.yaml.snakeyaml.Yaml

object YmlConfigurationS {
  
  private[teamcity] def loadOrCreate(teamcity: TeamCityS, observable: ObservableS): TeamCityConfigurationS = {
    try {
      val file = new YmlConfigurationFileS
      file.initialise(teamcity)
      observable.notifyObservers(new InformationS(s"Configuration stored in ${file.getPath}"))
      new YmlConfigurationS(file)
    } catch {
      case e: Exception => {
        observable.notifyObservers(new FailedToCreateYmlFileS(e))
        new EnvironmentVariableConfigurationS
      }
    }
  }
}

class YmlConfigurationS(file: YmlConfigurationFileS) extends TeamCityConfigurationS {
  private val configuration: Map[String, Any] = load(file)

  @throws(classOf[FileNotFoundException])
  private def load(configuration: File) = new Yaml().load(new FileReader(configuration)).asInstanceOf[Map[String, Any]]

  def host = configuration.get("host").asInstanceOf[String]

  def port = configuration.get("port").asInstanceOf[Integer]

  def filter(projects: Iterable[ProjectScala]) = {
    val ids = configuration.get("projects").asInstanceOf[List[String]]
    projects.filter(project => ids.contains(project.id))
  }

  def password = PasswordS.password(configuration.get("password").asInstanceOf[String])

  def username = UsernameS.username(configuration.get("user").asInstanceOf[String])

  def authorisation = authorisationFor(username, password)
}