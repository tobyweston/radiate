package bad.robot.radiate.teamcity

import argonaut.Argonaut._
import simplehttp.HttpResponse
import bad.robot.radiate.{ParseError, Unmarshaller}
import bad.robot.radiate.Error

import scalaz.\/
import scalaz.syntax.std.either._

class JsonProjectsUnmarshaller extends Unmarshaller[HttpResponse, Iterable[Project]] {
  def unmarshall(response: HttpResponse): Error \/ Iterable[Project] = {
    val json = new JsonResponse(response).body
    json.decodeEither[Projects].disjunction.leftMap(ParseError)
  }
}