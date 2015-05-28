package bad.robot.radiate.teamcity

import bad.robot.radiate.monitor.InformationS
import bad.robot.radiate.teamcity.StringOps._

class FailedToCreateYmlFileS(e: Exception) extends InformationS(
    s"Failed to create Yml configuration file (caused by an ${e.getClass.getSimpleName} ${defaultString(e.getMessage)}), falling back to use environment variables"
)

object StringOps {
    def defaultString(string: String) {
        if (string == null) () else string
    }
}
