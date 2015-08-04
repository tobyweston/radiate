package bad.robot.radiate.teamcity

import argonaut.Argonaut._
import bad.robot.http.HttpResponse
import bad.robot.radiate.Unmarshaller

class JsonBuildUnmarshaller extends Unmarshaller[HttpResponse, Build] {
  def unmarshall(response: HttpResponse): Build = {
    val json = new JsonResponse(response).body
    json.decodeEither[Build].valueOr(error => throw new Exception(error))
  }
}