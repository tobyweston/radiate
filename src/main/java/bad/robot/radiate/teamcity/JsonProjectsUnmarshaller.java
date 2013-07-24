package bad.robot.radiate.teamcity;

import bad.robot.http.HttpResponse;
import bad.robot.radiate.Unmarshaller;
import com.google.gson.Gson;

class JsonProjectsUnmarshaller implements Unmarshaller<Iterable<Project>> {

    private final HttpResponse response;

    public JsonProjectsUnmarshaller(HttpResponse response) {
        this.response = response;
    }

    @Override
    public Iterable<Project> unmarshall() {
        return new Gson().fromJson(response.getContent().asString(), Projects.class);
    }
}
