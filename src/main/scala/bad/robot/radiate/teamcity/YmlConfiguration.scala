package bad.robot.radiate.teamcity

import java.io.{File, FileNotFoundException, FileReader}

import bad.robot.radiate.config.{Username, Password}
import bad.robot.radiate.monitor.{Information, Observable}
import bad.robot.radiate.teamcity.Authorisation.authorisationFor
import org.yaml.snakeyaml.Yaml

object YmlConfiguration {
  
  private[teamcity] def loadOrCreate(teamcity: TeamCity, observable: Observable): DeprecatedConfig = {
    try {
      val file = new YmlConfigurationFile
      file.initialise(teamcity)
      observable.notifyObservers(new Information(s"Configuration stored in ${file.getPath}"))
      new YmlConfiguration(file)
    } catch {
      case e: Exception => {
        observable.notifyObservers(new FailedToCreateYmlFile(e))
        new EnvironmentVariableConfiguration
      }
    }
  }
}

class YmlConfiguration(file: YmlConfigurationFile) extends DeprecatedConfig {
  private val configuration: java.util.Map[String, Any] = load(file)

  @throws(classOf[FileNotFoundException])
  private def load(configuration: File) = new Yaml().load(new FileReader(configuration)).asInstanceOf[java.util.Map[String, Any]]


  def serverUrl = ???

  def host = Option(configuration.get("host").asInstanceOf[String])

  def port = configuration.get("port").asInstanceOf[Integer]

  def filter(projects: Iterable[Project]) = {
    val ids = configuration.get("projects").asInstanceOf[java.util.List[String]]
    projects.filter(project => ids.contains(project.id))
  }

  def password = Password.password(configuration.get("password").asInstanceOf[String])

  def username = Username.username(configuration.get("user").asInstanceOf[String])

  def authorisation = authorisationFor(username, password)
}