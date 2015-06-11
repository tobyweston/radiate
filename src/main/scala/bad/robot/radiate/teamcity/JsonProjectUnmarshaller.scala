package bad.robot.radiate.teamcity

import argonaut.Argonaut._
import bad.robot.http.HttpResponse
import bad.robot.radiate.UnmarshallerS

class JsonProjectUnmarshallerS extends UnmarshallerS[HttpResponse, FullProjectS] {
  def unmarshall(response: HttpResponse): FullProjectS = {
    val json = new JsonResponseS(response).body
    json.decodeEither[FullProjectS].valueOr(error => throw new Exception(error))
  }
}