package bad.robot.radiate.teamcity

import argonaut.Argonaut._
import bad.robot.http.HttpResponse
import bad.robot.radiate.UnmarshallerS
import com.google.gson.Gson

class JsonBuildUnmarshallerS extends UnmarshallerS[HttpResponse, BuildS] {
  def unmarshall(response: HttpResponse): BuildS = {
    val json = new JsonResponseS(response).body
    json.decodeEither[BuildS].valueOr(error => throw new Exception(error))
  }
}