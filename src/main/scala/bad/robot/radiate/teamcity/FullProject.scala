package bad.robot.radiate.teamcity

import argonaut.Argonaut._
import argonaut.DecodeJson
import bad.robot.radiate.HypermediaS

object ProjectScala {
  implicit def fullProjectDecoder: DecodeJson[ProjectScala] =
    DecodeJson(cursor => {
      for {
        id <- (cursor --\ "id").as[String]
        name <- (cursor --\ "name").as[String]
        href <- (cursor --\ "href").as[String]
        buildTypes <- (cursor --\ "buildTypes").as[BuildTypesScala]
      } yield {
        new ProjectScala(id, name, href, buildTypes)
      }
    })
}

case class ProjectScala(id: String, name: String, href: String, buildTypes: BuildTypesScala) extends TeamCityObjectS with HypermediaS {
  override def toString = s"$name ($id)"
}