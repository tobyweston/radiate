package bad.robot.radiate.teamcity

import argonaut.DecodeJson

object ProjectsScala {
  implicit def projectsDecoder: DecodeJson[ProjectsScala] = {
    DecodeJson(cursor => {
      (cursor --\ "project").as[List[ProjectScala]] map { new ProjectsScala(_) }
    })
  }
}

class ProjectsScala(projects: List[ProjectScala]) extends TeamCityObjectS with Iterable[ProjectScala] {
  def iterator = projects.iterator
}