package bad.robot.radiate.teamcity

import argonaut.Argonaut._
import bad.robot.http.HttpResponse
import bad.robot.radiate.{ParseError, Unmarshaller, Error}

import scalaz.\/

class JsonBuildUnmarshaller extends Unmarshaller[HttpResponse, Build] {
  def unmarshall(response: HttpResponse): Error \/ Build = {
    val json = new JsonResponse(response).body
    json.decodeEither[Build].leftMap(ParseError)
  }
}