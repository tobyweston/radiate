package bad.robot.radiate.teamcity

import org.apache.commons.lang3.RandomStringUtils

object AnyS {
  def projects: ProjectsScala = new ProjectsScala(List(
    new FullProjectS(anyId, anyName, anyHref, buildTypes),
    new FullProjectS(anyId, anyName, anyHref, buildTypes)
  ))

  def buildTypes = new BuildTypesScala(List(buildType, buildType, buildType))

  def buildType = new BuildTypeScala(anyId, anyName, anyHref, "project:" + anyString(8), "pid:" + anyString(5))

  private def anyHref = s"/href:${anyString(12)}"
  private def anyName = s"name:${anyString(11)}"
  private def anyId = s"id:${anyString(8)}"
  private def anyString(length: Int) = RandomStringUtils.random(length, true, false)
}