package bad.robot.radiate.teamcity;

import bad.robot.http.HttpResponse;
import bad.robot.radiate.Unmarshaller;
import com.google.gson.Gson;

class JsonProjectUnmarshaller implements Unmarshaller<HttpResponse, Project> {

    @Override
    public Project unmarshall(HttpResponse response) {
        return new Gson().fromJson(new JsonResponse(response).body(), Project.class);
    }
}
