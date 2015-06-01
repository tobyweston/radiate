package bad.robot.radiate.teamcity

import bad.robot.http.HttpResponse
import bad.robot.radiate.UnmarshallerS
import com.google.gson.Gson

class JsonBuildUnmarshallerS extends UnmarshallerS[HttpResponse, BuildS] {
  def unmarshall(response: HttpResponse): BuildS = {
    val build = new Gson().fromJson(new JsonResponseS(response).body, classOf[RunningBuildS])
    if (build.runInformation == null) new BuildS(build.id, build.number, build.href, build.statusText, build.statusText, build.startDate, build.finishDate, build.buildType)
    else build
  }
}