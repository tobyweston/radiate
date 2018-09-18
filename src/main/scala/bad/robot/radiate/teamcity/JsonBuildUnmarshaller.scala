package bad.robot.radiate.teamcity

import argonaut.Argonaut._
import simplehttp.HttpResponse
import bad.robot.radiate.{ParseError, Unmarshaller, Error}
import scalaz.syntax.std.either._
import scalaz.\/

class JsonBuildUnmarshaller extends Unmarshaller[HttpResponse, Build] {
  def unmarshall(response: HttpResponse): Error \/ Build = {
    val json = new JsonResponse(response).body
    json.decodeEither[Build].disjunction.leftMap(ParseError)
  }
}