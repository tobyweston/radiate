package bad.robot.radiate.teamcity

import bad.robot.radiate.monitor.Information
import bad.robot.radiate.teamcity.StringOps._

class FailedToCreateYmlFile(e: Exception) extends Information(
  s"Failed to create Yml configuration file (caused by an ${e.getClass.getSimpleName} ${defaultString(e.getMessage)}), falling back to use environment variables"
)

object StringOps {
  def defaultString(string: String): String = {
    if (string == null) "" else string.toString
  }
}
