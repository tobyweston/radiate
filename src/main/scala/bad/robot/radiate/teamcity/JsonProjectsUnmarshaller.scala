package bad.robot.radiate.teamcity

import argonaut.Argonaut._
import bad.robot.http.HttpResponse
import bad.robot.radiate.UnmarshallerS

class JsonProjectsUnmarshallerS extends UnmarshallerS[HttpResponse, Iterable[ProjectScala]] {
  def unmarshall(response: HttpResponse): Iterable[ProjectScala] = {
    val json = new JsonResponseS(response).body
    json.decodeEither[ProjectsScala].valueOr(error => throw new Exception(error))
  }
}