package bad.robot.radiate.teamcity

object BuildLocatorBuilder {
  
  def latest(buildType: BuildType): BuildLocatorBuilder = {
    new BuildLocatorBuilder().`with`(buildType)
  }

  def running(buildType: BuildType): BuildLocatorBuilder = {
    new BuildLocatorBuilder().`with`(buildType).running
  }
}

class BuildLocatorBuilder {
  private val locator = new StringBuilder

  private[teamcity] def `with`(`type`: BuildType): BuildLocatorBuilder = {
    withSeparator.append("buildType:").append(`type`.id)
    this
  }

  private[teamcity] def running: BuildLocatorBuilder = {
    withSeparator.append("running:true")
    this
  }

  private def withSeparator: StringBuilder = {
    if (locator.nonEmpty) locator.append(",")
    locator
  }

  private[teamcity] def build: String = locator.toString()
}