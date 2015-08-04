package bad.robot.radiate.teamcity

import argonaut.Argonaut._
import bad.robot.http.HttpResponse
import bad.robot.radiate.Unmarshaller

class JsonProjectsUnmarshaller extends Unmarshaller[HttpResponse, Iterable[Project]] {
  def unmarshall(response: HttpResponse): Iterable[Project] = {
    val json = new JsonResponse(response).body
    json.decodeEither[Projects].valueOr(error => throw new Exception(error))
  }
}