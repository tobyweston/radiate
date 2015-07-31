package bad.robot.radiate.teamcity

import bad.robot.radiate.Unknown

class NoBuildS extends BuildS("", "", "/", "", "", "", None, new BuildTypeScala("", "", "/", "", ""), None) {

  override def status = Unknown
}