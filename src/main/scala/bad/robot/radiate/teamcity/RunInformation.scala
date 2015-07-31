package bad.robot.radiate.teamcity

import argonaut.DecodeJson

object RunInformationS {
  implicit def runInformationDecoder: DecodeJson[RunInformationS] = {
    DecodeJson(cursor => {
      for {
        percentageComplete <- cursor.get[Int]("percentageComplete")
        elapsedSeconds <- cursor.get[Int]("elapsedSeconds")
        estimatedTotalSeconds <- cursor.get[Int]("estimatedTotalSeconds")
        outdated <- cursor.get[Boolean]("outdated")
        probablyHanging <- cursor.get[Boolean]("probablyHanging")
      } yield {
        RunInformationS(percentageComplete, elapsedSeconds, estimatedTotalSeconds, outdated, probablyHanging)
      }
    })
  }
}

case class RunInformationS(percentageComplete: Integer, elapsedSeconds: Integer, estimatedTotalSeconds: Integer, outdated: Boolean, probablyHanging: Boolean) extends TeamCityObjectS
