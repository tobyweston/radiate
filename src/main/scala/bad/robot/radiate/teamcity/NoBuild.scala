package bad.robot.radiate.teamcity

import bad.robot.radiate.Unknown

class NoBuildS extends BuildS("", "", "/", "", "", "", "", new BuildTypeScala("", "", "/", "", "")) {

  override def status = Unknown
}