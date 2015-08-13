package bad.robot.radiate.teamcity

import argonaut.Argonaut._
import bad.robot.http.HttpResponse
import bad.robot.radiate.Unmarshaller
import bad.robot.radiate.Error
import bad.robot.radiate.ParseError

import scalaz.\/

class JsonProjectUnmarshaller extends Unmarshaller[HttpResponse, Project] {
  def unmarshall(response: HttpResponse): Error \/ Project = {
    val json = new JsonResponse(response).body
    json.decodeEither[Project].leftMap(ParseError)
  }
}