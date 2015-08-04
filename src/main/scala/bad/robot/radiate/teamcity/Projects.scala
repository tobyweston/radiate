package bad.robot.radiate.teamcity

import argonaut.DecodeJson

object Projects {
  implicit def projectsDecoder: DecodeJson[Projects] = {
    DecodeJson(cursor => {
      (cursor --\ "project").as[List[Project]] map { new Projects(_) }
    })
  }
}

class Projects(projects: List[Project]) extends TeamCityObject with Iterable[Project] {
  def iterator = projects.iterator
}