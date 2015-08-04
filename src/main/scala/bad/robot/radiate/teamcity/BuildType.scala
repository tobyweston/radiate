package bad.robot.radiate.teamcity

import bad.robot.radiate.Hypermedia
import argonaut._
import Argonaut._

object BuildType {
  implicit def buildTypeCodec: DecodeJson[BuildType] = {
    DecodeJson(cursor => {
      for {
        id <- (cursor --\ "id").as[String]
        name <- (cursor --\ "name").as[String]
        href <- (cursor --\ "href").as[String]
        projectName <- (cursor --\ "projectName").as[String]
        projectId <- (cursor --\ "projectId").as[String]
      } yield {
        BuildType(id, name, href, projectName, projectId)
      }
    })
  }
}

case class BuildType(id: String, name: String, href: String, projectName: String, projectId: String) extends TeamCityObject with Hypermedia
