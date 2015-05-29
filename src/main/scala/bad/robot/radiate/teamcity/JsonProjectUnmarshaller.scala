package bad.robot.radiate.teamcity

import bad.robot.http.HttpResponse
import bad.robot.radiate.UnmarshallerS
import com.google.gson.Gson

class JsonProjectUnmarshallerS extends UnmarshallerS[HttpResponse, ProjectScala] {
  def unmarshall(response: HttpResponse): ProjectScala = {
    val project = new Gson().fromJson(new JsonResponse(response).body, classOf[FullProjectS])
    if (!project.iterator.hasNext) new ProjectScala(project.id, project.name, project.href)
    else project
  }
}