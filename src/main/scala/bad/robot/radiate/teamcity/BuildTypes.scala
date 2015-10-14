package bad.robot.radiate.teamcity

import argonaut.DecodeJson

object BuildTypes {
  implicit def buildTypesDecoder: DecodeJson[BuildTypes] = {
    DecodeJson(cursor => {
      (cursor --\ "buildType").as[List[BuildType]] map { BuildTypes(_) }
    })
  }
}

case class BuildTypes(list: List[BuildType]) extends TeamCityObject with Iterable[BuildType] {
  def iterator: Iterator[BuildType] = list.iterator
}