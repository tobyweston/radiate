package bad.robot.radiate.teamcity;

import bad.robot.http.CommonHttpClient;
import bad.robot.http.HttpResponse;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static bad.robot.http.HeaderList.headers;
import static bad.robot.http.HeaderPair.header;
import static bad.robot.http.HttpClients.anApacheClient;
import static bad.robot.http.matchers.Matchers.has;
import static bad.robot.http.matchers.Matchers.status;
import static org.hamcrest.MatcherAssert.assertThat;

public class TeamCityTest {

    private final String host = "localhost";
    private final CommonHttpClient http = anApacheClient();
    private final JsonProjectsUnmarshaller unmarshaller = new JsonProjectsUnmarshaller();

    @Test
    public void getBuildsForAProject() throws MalformedURLException {
        TeamCity teamcity = new TeamCity(new Server(host, 8111), http, unmarshaller);
        Iterable<Project> projects = teamcity.retrieveProjects();
        for (Project project : projects) {
            System.out.println(project);
        }
    }

    private URL proxy() {
        try {
            return new URL("http://localhost:8888");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
