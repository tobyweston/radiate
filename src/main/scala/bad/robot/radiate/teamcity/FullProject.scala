package bad.robot.radiate.teamcity

import argonaut.Argonaut._
import argonaut.DecodeJson

object FullProjectS {
  implicit def fullProjectDecoder: DecodeJson[FullProjectS] =
    DecodeJson(cursor => {
      for {
        id <- (cursor --\ "id").as[String]
        name <- (cursor --\ "name").as[String]
        href <- (cursor --\ "href").as[String]
        buildTypes <- (cursor --\ "buildTypes").as[BuildTypesScala]
      } yield {
        new FullProjectS(id, name, href, buildTypes)
      }
    })
}

class FullProjectS(id: String, name: String, href: String, val buildTypes: BuildTypesScala) extends ProjectScala(id, name, href) {
  override def iterator = buildTypes.iterator
}