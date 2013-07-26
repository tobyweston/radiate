package bad.robot.radiate.teamcity;

import bad.robot.http.CommonHttpClient;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static bad.robot.http.HttpClients.anApacheClient;
import static bad.robot.radiate.Url.url;
import static java.lang.String.format;

public class TeamCityTest {

    private final String host = getEnvironmentVariable("teamcity.host");

    private final CommonHttpClient http = anApacheClient();

    private final JsonProjectsUnmarshaller unmarshaller = new JsonProjectsUnmarshaller();
    @Test
    public void getBuildsTypes() throws MalformedURLException {
        TeamCity teamcity = new TeamCity(new Server(host, 8111), http, unmarshaller);
        Iterable<Project> projects = teamcity.retrieveProjects();
        teamcity.retrieveFullProjectInformation(projects);
    }

    private URL proxy() {
        return url("http://localhost:8888");
    }

    private String getEnvironmentVariable(String variable) {
        String var = System.getenv(variable);
        if (var == null) throw new IllegalArgumentException(format("Please set environment variable '%s'", variable));
        return var;
    }

}
