package bad.robot.radiate.teamcity

import bad.robot.http.HttpResponse
import bad.robot.radiate.UnmarshallerS
import com.google.gson.Gson

class JsonProjectsUnmarshallerS extends UnmarshallerS[HttpResponse, Iterable[ProjectScala]] {
  def unmarshall(response: HttpResponse): Iterable[ProjectScala] = {
    new Gson().fromJson(new JsonResponseS(response).body, classOf[ProjectsScala])
  }
}