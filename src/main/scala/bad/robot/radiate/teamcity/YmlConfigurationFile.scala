package bad.robot.radiate.teamcity

import java.io.{File, IOException}

import org.apache.commons.io.FileUtils.writeStringToFile
import org.yaml.snakeyaml.Yaml

class YmlConfigurationFileS extends File(System.getProperty("user.home") + File.separator + ".radiate" + File.separator + "config.yml") {

  private val fallback = new EnvironmentVariableConfigurationS

  @throws(classOf[IOException])
  private[teamcity] def initialise(teamcity: TeamCity) {
    createFolder
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
        "projects" -> getProjectIds(teamcity),
        "host" -> fallback.host,
        "port" -> fallback.port,
        "user" -> null,
        "password" -> null
      )
      writeStringToFile(this, yaml.dump(data))
    } catch {
      case e: Exception => {
        deleteOnExit()
        throw e
      }
    }
  }

  private def getProjectIds(teamcity: TeamCity): List[String] = {
    import scala.collection.JavaConverters._
    val projects = teamcity.retrieveProjects.asScala
    projects.map(_.getId).toList
  }

  private def createFolder() {
    new File(System.getProperty("user.home") + File.separator + ".radiate").mkdirs
  }
}