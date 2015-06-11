package bad.robot.radiate.teamcity

import com.google.gson.annotations.SerializedName

class ProjectsScala(@SerializedName("project") private val projects: List[ProjectScala]) extends TeamCityObjectS with Iterable[ProjectScala] {
  def iterator = projects.iterator
}