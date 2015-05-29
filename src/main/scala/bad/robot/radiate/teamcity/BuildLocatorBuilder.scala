package bad.robot.radiate.teamcity

object BuildLocatorBuilderS {
  
  def latest(buildType: BuildTypeScala): BuildLocatorBuilderS = {
    new BuildLocatorBuilderS().`with`(buildType)
  }

  def running(buildType: BuildTypeScala): BuildLocatorBuilderS = {
    new BuildLocatorBuilderS().`with`(buildType).running
  }
}

class BuildLocatorBuilderS {
  private val locator = new StringBuilder

  private[teamcity] def `with`(`type`: BuildTypeScala): BuildLocatorBuilderS = {
    withSeparator.append("buildType:").append(`type`.id)
    this
  }

  private[teamcity] def running: BuildLocatorBuilderS = {
    withSeparator.append("running:true")
    this
  }

  private def withSeparator: StringBuilder = {
    if (locator.length > 0) locator.append(",")
    locator
  }

  private[teamcity] def build: String = locator.toString()
}