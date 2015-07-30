package bad.robot.radiate.teamcity

import java.util.concurrent.atomic.AtomicInteger

import org.apache.commons.lang3.RandomStringUtils

object AnyS {

  private val number = new AtomicInteger(0)

  def project(buildTypes: BuildTypesScala) = new ProjectScala(anyId, anyName, anyHref, buildTypes)

  def projects = new ProjectsScala(List(
    new ProjectScala(anyId, anyName, anyHref, buildTypes),
    new ProjectScala(anyId, anyName, anyHref, buildTypes)
  ))

  def buildTypes = new BuildTypesScala(List(buildType, buildType, buildType))

  def buildType = new BuildTypeScala(anyId, anyName, anyHref, "project:" + anyString(8), "pid:" + anyString(5))

  def build = new BuildS(anyId, incrementingNumber, anyHref, "SUCCESS", "Success", anyString(10), anyString(10), buildType)

  def runningBuild = {
    val runInformation = new RunInformationS(74, 12, 23, false, false)
    new RunningBuildS(anyId, incrementingNumber, anyHref, "SUCCESS", "Success", anyString(10), anyString(10), buildType, runInformation)
  }

  def runningBuildPercentageCompleteAt(percentageComplete: Int) = {
    val runInformation = new RunInformationS(percentageComplete, 12, 23, false, false)
    new RunningBuildS(anyId, incrementingNumber, anyHref, "SUCCESS", "Success", anyString(10), anyString(10), buildType, runInformation)
  }

  private def incrementingNumber: String = {
    Integer.toString(number.incrementAndGet)
  }

  private def anyHref = s"/href:${anyString(12)}"
  private def anyName = s"name:${anyString(11)}"
  private def anyId = s"id:${anyString(8)}"
  private def anyString(length: Int) = RandomStringUtils.random(length, true, false)
}
