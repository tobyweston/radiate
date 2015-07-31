package bad.robot.radiate.teamcity

import argonaut.DecodeJson
import bad.robot.radiate.{Idle, _}

object BuildS {
  implicit def buildDecoder: DecodeJson[BuildS] = {
    DecodeJson(cursor => {
      for {
        id <- cursor.get[Int]("id")
        number <- cursor.get[String]("number")
        href <- cursor.get[String]("href")
        status <- cursor.get[String]("status")
        statusText <- cursor.get[String]("statusText")
        start <- cursor.get[String]("startDate")
        finish <- cursor.get[Option[String]]("finishDate")
        runningInfo <- cursor.get[Option[RunInformationS]]("running-info")
        buildType <- cursor.get[BuildTypeScala]("buildType")
      } yield {
        BuildS(id.toString, number, href, status, statusText, start, finish, buildType, runningInfo)
      }
    })
  }
}

case class BuildS(id: String, number: String, href: String, private val _status: String, statusText: String, startDate: String, finishDate: Option[String], buildType: BuildTypeScala, runInformation: Option[RunInformationS]) extends TeamCityObjectS with HypermediaS with MonitorableS {

  def status: StatusS = {
    _status.toLowerCase match {
      case "success" => Ok
      case "failure" => Broken
      case _ => Unknown
    }
  }

  def statusString = _status

  def activity: ActivityS = runInformation match {
    case Some(_) => Progressing
    case None => Idle
  }

  def progress: ProgressS = {
    runInformation.map(run => new ProgressS(run.percentageComplete, 100)).getOrElse(new NullProgressS)
  }
}