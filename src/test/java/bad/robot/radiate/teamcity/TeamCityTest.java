package bad.robot.radiate.teamcity;

import bad.robot.http.CommonHttpClient;
import bad.robot.http.HttpResponse;
import bad.robot.radiate.Environment;
import bad.robot.radiate.Unmarshaller;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static bad.robot.http.HttpClients.anApacheClient;
import static bad.robot.radiate.Url.url;

public class TeamCityTest {

    private final String host = Environment.getEnvironmentVariable("teamcity.host");

    private final CommonHttpClient http = anApacheClient();
    private final Unmarshaller<HttpResponse, Iterable<Project>> projects = new JsonProjectsUnmarshaller();
    private final Unmarshaller<HttpResponse, Project> project = new JsonProjectUnmarshaller();

    @Test
    public void getBuildsTypes() throws MalformedURLException {
        TeamCity teamcity = new TeamCity(new Server(host, 8111), http, projects, project);
        Iterable<Project> projects = teamcity.retrieveProjects();
        Iterable<BuildType> buildTypes = teamcity.retrieveBuildTypes(projects);
        for (BuildType buildType : buildTypes) {
            Build build = teamcity.retrieveLatestBuild(buildType);
            System.out.printf("%s: %s (%s) - %s%n", build.getBuildType(), build.getNumber(), build.getNumber(), build.getStatusText());
        }
    }

    private URL proxy() {
        return url("http://localhost:8888");
    }

}
