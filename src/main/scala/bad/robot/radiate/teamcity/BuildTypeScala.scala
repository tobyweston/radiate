package bad.robot.radiate.teamcity

import bad.robot.radiate.HypermediaS
import argonaut._
import Argonaut._

object BuildTypeScala {
  implicit def buildTypeCodec: DecodeJson[BuildTypeScala] = {
    DecodeJson(cursor => {
      for {
        id <- (cursor --\ "id").as[String]
        name <- (cursor --\ "name").as[String]
        href <- (cursor --\ "href").as[String]
        projectName <- (cursor --\ "projectName").as[String]
        projectId <- (cursor --\ "projectId").as[String]
      } yield {
        BuildTypeScala(id, name, href, projectName, projectId)
      }
    })
  }
}

case class BuildTypeScala(id: String, name: String, href: String, projectName: String, projectId: String) extends TeamCityObjectS with HypermediaS
