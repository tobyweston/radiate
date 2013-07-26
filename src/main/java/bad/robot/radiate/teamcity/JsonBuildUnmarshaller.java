package bad.robot.radiate.teamcity;

import bad.robot.http.HttpResponse;
import bad.robot.radiate.Unmarshaller;
import com.google.gson.Gson;

class JsonBuildUnmarshaller implements Unmarshaller<HttpResponse, Build> {

    @Override
    public Build unmarshall(HttpResponse response) {
        RunningBuild build = new Gson().fromJson(response.getContent().asString(), RunningBuild.class);
        if (build.getRunInformation() == null)
            return new Build(build.getId(), build.getNumber(), build.getHref(), build.getStatusText(), build.getStartDate(), build.getFinishDate(), build.getBuildType());
        return build;
    }
}
