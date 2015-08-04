package bad.robot.radiate.teamcity

import java.util.concurrent.atomic.AtomicInteger

import org.apache.commons.lang3.RandomStringUtils

object Any {

  private val number = new AtomicInteger(0)

  def project(buildTypes: BuildTypes) = new Project(anyId, anyName, anyHref, buildTypes)

  def projects = new Projects(List(
    new Project(anyId, anyName, anyHref, buildTypes),
    new Project(anyId, anyName, anyHref, buildTypes)
  ))

  def buildTypes = new BuildTypes(List(buildType, buildType, buildType))

  def buildType = new BuildType(anyId, anyName, anyHref, "project:" + anyString(8), "pid:" + anyString(5))

  def build = new Build(anyId, incrementingNumber, anyHref, "SUCCESS", "Success", anyString(10), Some(anyString(10)), buildType, None)

  def runningBuild = {
    val runInformation = new RunInformation(74, 12, 23, false, false)
    new Build(anyId, incrementingNumber, anyHref, "SUCCESS", "Success", anyString(10), Some(anyString(10)), buildType, Some(runInformation))
  }

  def runningBuildPercentageCompleteAt(percentageComplete: Int) = {
    val runInformation = new RunInformation(percentageComplete, 12, 23, false, false)
    new Build(anyId, incrementingNumber, anyHref, "SUCCESS", "Success", anyString(10), Some(anyString(10)), buildType, Some(runInformation))
  }

  private def incrementingNumber: String = {
    Integer.toString(number.incrementAndGet)
  }

  private def anyHref = s"/href:${anyString(12)}"
  private def anyName = s"name:${anyString(11)}"
  private def anyId = s"id:${anyString(8)}"
  private def anyString(length: Int) = RandomStringUtils.random(length, true, false)
}
