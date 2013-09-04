package bad.robot.radiate.teamcity;

import bad.robot.http.HttpResponse;
import bad.robot.radiate.Unmarshaller;
import com.google.gson.Gson;

class JsonProjectUnmarshaller implements Unmarshaller<HttpResponse, Project> {

    @Override
    public Project unmarshall(HttpResponse response) {
        Project project = new Gson().fromJson(new JsonResponse(response).body(), FullProject.class);
        if (!project.iterator().hasNext())
            return new Project(project.getId(), project.getName(), project.getHref());
        return project;
    }
}
