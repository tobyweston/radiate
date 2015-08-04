package bad.robot.radiate.teamcity

import argonaut.Argonaut._
import argonaut.DecodeJson
import bad.robot.radiate.Hypermedia

object Project {
  implicit def projectDecoder: DecodeJson[Project] = {
    DecodeJson(cursor => {
      for {
        id <- (cursor --\ "id").as[String]
        name <- (cursor --\ "name").as[String]
        href <- (cursor --\ "href").as[String]
        buildTypes <- (cursor --\ "buildTypes").as[Option[BuildTypes]]
      } yield {
        Project(id, name, href, buildTypes.getOrElse(BuildTypes(List())))
      }
    })
  }
}

case class Project(id: String, name: String, href: String, buildTypes: BuildTypes) extends TeamCityObject with Hypermedia {
  override def toString = s"$name ($id)"
}