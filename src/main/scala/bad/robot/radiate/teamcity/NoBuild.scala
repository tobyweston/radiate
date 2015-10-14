package bad.robot.radiate.teamcity

import bad.robot.radiate.Unknown

class NoBuild extends Build("", "", "/", "", "", "", None, new BuildType("", "", "/", "", ""), None) {

  override def status = Unknown
}