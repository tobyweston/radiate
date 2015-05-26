package bad.robot.radiate.teamcity

import com.google.gson.annotations.SerializedName

case class BuildTypesScala(@SerializedName("buildType") private val buildTypes: List[BuildTypeScala]) extends TeamCityObjectS with Iterable[BuildTypeScala] {

  def iterator: Iterator[BuildTypeScala] = {
    buildTypes.iterator
  }
}