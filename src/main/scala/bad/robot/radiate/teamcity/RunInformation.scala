package bad.robot.radiate.teamcity

import argonaut.DecodeJson

object RunInformation {
  implicit def runInformationDecoder: DecodeJson[RunInformation] = {
    DecodeJson(cursor => {
      for {
        percentageComplete <- cursor.get[Int]("percentageComplete")
        elapsedSeconds <- cursor.get[Int]("elapsedSeconds")
        estimatedTotalSeconds <- cursor.get[Int]("estimatedTotalSeconds")
        outdated <- cursor.get[Boolean]("outdated")
        probablyHanging <- cursor.get[Boolean]("probablyHanging")
      } yield {
        RunInformation(percentageComplete, elapsedSeconds, estimatedTotalSeconds, outdated, probablyHanging)
      }
    })
  }
}

case class RunInformation(percentageComplete: Integer, elapsedSeconds: Integer, estimatedTotalSeconds: Integer, outdated: Boolean, probablyHanging: Boolean) extends TeamCityObject
