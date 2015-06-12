package bad.robot.radiate.teamcity

import argonaut.Argonaut._
import argonaut.DecodeJson
import bad.robot.radiate.HypermediaS

object ProjectScala {
  implicit def projectDecoder: DecodeJson[ProjectScala] = {
    DecodeJson(cursor => {
      for {
        id <- (cursor --\ "id").as[String]
        name <- (cursor --\ "name").as[String]
        href <- (cursor --\ "href").as[String]
        buildTypes <- (cursor --\ "buildTypes").as[Option[BuildTypesScala]]
      } yield {
        ProjectScala(id, name, href, buildTypes.getOrElse(BuildTypesScala(List())))
      }
    })
  }
}

case class ProjectScala(id: String, name: String, href: String, buildTypes: BuildTypesScala) extends TeamCityObjectS with HypermediaS {
  override def toString = s"$name ($id)"
}