package bad.robot.radiate.teamcity;

import bad.robot.http.HttpResponse;
import bad.robot.radiate.Unmarshaller;
import com.google.gson.Gson;

class JsonBuildUnmarshaller implements Unmarshaller<HttpResponse, Build> {

    @Override
    public Build unmarshall(HttpResponse response) {
        return new Gson().fromJson(response.getContent().asString(), Build.class);
    }
}
