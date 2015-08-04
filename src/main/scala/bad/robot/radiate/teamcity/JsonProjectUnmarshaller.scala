package bad.robot.radiate.teamcity

import argonaut.Argonaut._
import bad.robot.http.HttpResponse
import bad.robot.radiate.Unmarshaller

class JsonProjectUnmarshaller extends Unmarshaller[HttpResponse, Project] {
  def unmarshall(response: HttpResponse): Project = {
    val json = new JsonResponse(response).body
    json.decodeEither[Project].valueOr(error => throw new Exception(error))
  }
}