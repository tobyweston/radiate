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

    private final String host = "http://localhost:8111";
    private final CommonHttpClient http = anApacheClient();

    @Test
    public void getBuildsForAProject() throws MalformedURLException {
        URL url = new URL(host + TeamCityEndpoint.projectsEndpoint + "_Root");
        HttpResponse response = http.get(url, headers(
                header("Accept", "application/json")
        ));
        assertThat(response, has(status(200)));
        System.out.println(response.getContent());
    }

    private URL proxy() {
        try {
            return new URL("http://localhost:8888");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
