package bad.robot.radiate.teamcity

import com.google.gson.annotations.SerializedName

class ProjectsScala(@SerializedName("project") private val projects: List[FullProjectS]) extends TeamCityObjectS with Iterable[FullProjectS] {
  def iterator = projects.iterator
}