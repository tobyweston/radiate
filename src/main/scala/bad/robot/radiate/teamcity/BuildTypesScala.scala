package bad.robot.radiate.teamcity

import argonaut.DecodeJson

object BuildTypesScala {
  implicit def buildTypesDecoder: DecodeJson[BuildTypesScala] = {
    DecodeJson(cursor => {
      (cursor --\ "buildType").as[List[BuildTypeScala]] map { BuildTypesScala(_) }
    })
  }
}

case class BuildTypesScala(buildTypes: List[BuildTypeScala]) extends TeamCityObjectS with Iterable[BuildTypeScala] {

  def iterator: Iterator[BuildTypeScala] = {
    buildTypes.iterator
  }
}