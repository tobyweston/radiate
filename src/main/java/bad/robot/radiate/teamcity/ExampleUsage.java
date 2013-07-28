package bad.robot.radiate.teamcity;

import bad.robot.http.CommonHttpClient;
import bad.robot.http.HttpResponse;
import bad.robot.radiate.Environment;
import bad.robot.radiate.Unmarshaller;

import java.net.MalformedURLException;

import static bad.robot.http.HttpClients.anApacheClient;

public class ExampleUsage {

    public static void main(String... args) throws MalformedURLException {
        String host = Environment.getEnvironmentVariable("teamcity.host");

        CommonHttpClient http = anApacheClient();
        Unmarshaller<HttpResponse, Iterable<Project>> projectsUnmarshaller = new JsonProjectsUnmarshaller();
        Unmarshaller<HttpResponse, Project> projectUnmarshaller = new JsonProjectUnmarshaller();
        JsonBuildUnmarshaller buildUnmarshaller = new JsonBuildUnmarshaller();

        TeamCity teamcity = new TeamCity(new Server(host), http, projectsUnmarshaller, projectUnmarshaller, buildUnmarshaller);

        Iterable<Project> projects = teamcity.retrieveProjects();
        Iterable<BuildType> buildTypes = teamcity.retrieveBuildTypes(projects);
        for (BuildType buildType : buildTypes) {
            Build build = teamcity.retrieveLatestBuild(buildType);
            System.out.printf("%s: #%s (id:%s) - %s (%s)%n", build.getBuildType().getName(), build.getNumber(), build.getId(), build.getStatus(), build.getStatusText());
        }
    }
}
