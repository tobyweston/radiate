package bad.robot.radiate.teamcity;

import bad.robot.http.HttpResponse;
import bad.robot.radiate.Unmarshaller;
import com.google.gson.Gson;

@Deprecated
class JsonProjectsUnmarshaller implements Unmarshaller<HttpResponse, Iterable<Project>> {

    @Override
    public Iterable<Project> unmarshall(HttpResponse response) {
        return new Gson().fromJson(new JsonResponse(response).body(), Projects.class);
    }
}
