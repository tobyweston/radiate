package bad.robot.radiate.teamcity

import argonaut.Argonaut._
import bad.robot.http.HttpResponse
import bad.robot.radiate.UnmarshallerS

class JsonProjectUnmarshallerS extends UnmarshallerS[HttpResponse, ProjectScala] {
  def unmarshall(response: HttpResponse): ProjectScala = {
    val json = new JsonResponseS(response).body
    json.decodeEither[ProjectScala].valueOr(error => throw new Exception(error))
  }
}