package bad.robot.radiate.teamcity

import java.io.{File, IOException}

import org.apache.commons.io.FileUtils.writeStringToFile
import org.yaml.snakeyaml.Yaml
import collection.JavaConverters._

class YmlConfigurationFile extends File(System.getProperty("user.home") + File.separator + ".radiate" + File.separator + "config.yml") {

  private val fallback = new EnvironmentVariableConfiguration

  @throws(classOf[IOException])
  private[teamcity] def initialise(teamcity: TeamCity) {
    createFolder()
    val created = createNewFile
    if (created) populateConfiguration(teamcity)
  }

  @throws(classOf[IOException])
  override def createNewFile = if (exists && length == 0) true else super.createNewFile

  @throws(classOf[IOException])
  private def populateConfiguration(teamcity: TeamCity) {
    try {
      val yaml = new Yaml
      val data = Map[String, Any](
        "projects" -> getProjectIds(teamcity).asJava,
        "host" -> fallback.host,
        "port" -> fallback.port,
        "user" -> null,
        "password" -> null
      )
      writeStringToFile(this, yaml.dump(data.asJava))
    } catch {
      case e: Exception => {
        deleteOnExit()
        throw e
      }
    }
  }

  private def getProjectIds(teamcity: TeamCity): List[String] = {
    val projects = teamcity.retrieveProjects
    projects.map(_.id).toList
  }

  private def createFolder() {
    sys.props.get("user.home").map(home => {
      new File(home + File.separator + ".radiate")
    }).getOrElse(throw new Exception(s"The system property 'user.home' is not set, this is very strange.")).mkdirs
  }
}