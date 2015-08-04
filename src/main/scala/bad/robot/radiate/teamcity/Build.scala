package bad.robot.radiate.teamcity

import argonaut.DecodeJson
import bad.robot.radiate.{Idle, _}

object Build {
  implicit def buildDecoder: DecodeJson[Build] = {
    DecodeJson(cursor => {
      for {
        id <- cursor.get[Int]("id")
        number <- cursor.get[String]("number")
        href <- cursor.get[String]("href")
        status <- cursor.get[String]("status")
        statusText <- cursor.get[String]("statusText")
        start <- cursor.get[String]("startDate")
        finish <- cursor.get[Option[String]]("finishDate")
        runningInfo <- cursor.get[Option[RunInformation]]("running-info")
        buildType <- cursor.get[BuildType]("buildType")
      } yield {
        Build(id.toString, number, href, status, statusText, start, finish, buildType, runningInfo)
      }
    })
  }
}

case class Build(id: String, number: String, href: String, private val _status: String, statusText: String, startDate: String, finishDate: Option[String], buildType: BuildType, runInformation: Option[RunInformation]) extends TeamCityObject with Hypermedia with Monitorable {

  def status: Status = {
    _status.toLowerCase match {
      case "success" => Ok
      case "failure" => Broken
      case _ => Unknown
    }
  }

  def statusString = _status

  def activity: Activity = runInformation match {
    case Some(_) => Progressing
    case None => Idle
  }

  def progress: Progress = {
    runInformation.map(run => new Progress(run.percentageComplete, 100)).getOrElse(new NullProgress)
  }
}