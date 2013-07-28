package bad.robot.radiate.teamcity;

import bad.robot.http.*;
import bad.robot.radiate.Environment;
import bad.robot.radiate.Unmarshaller;
import org.hamcrest.Matchers;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static bad.robot.http.EmptyHeaders.emptyHeaders;
import static bad.robot.http.HeaderList.headers;
import static bad.robot.http.HeaderPair.header;
import static bad.robot.http.HttpClients.anApacheClient;
import static bad.robot.radiate.Url.url;
import static org.hamcrest.MatcherAssert.assertThat;

public class TeamCityTest {

    @Rule public Mockery context = new JUnit4Mockery();
    private final HttpClient http = context.mock(HttpClient.class);

    private final HttpResponse ok = new StringHttpResponse(200, "OK", "", emptyHeaders());
    private final HttpResponse error = new StringHttpResponse(500, "Yuk", "", emptyHeaders());
    private final Projects expectedProjects = new Projects();

    private final Unmarshaller<HttpResponse, Iterable<Project>> projects = context.mock(Unmarshaller.class, "projects");
    private final Unmarshaller<HttpResponse, Project> project = context.mock(Unmarshaller.class, "project");
    private final TeamCity teamcity = new TeamCity(new Server("example.com"), http, projects, project);


    @Test
    @Ignore
    public void exampleUsage() throws MalformedURLException {
        String host = Environment.getEnvironmentVariable("teamcity.host");

        CommonHttpClient http = anApacheClient();
        Unmarshaller<HttpResponse, Iterable<Project>> projectsUnmarshaller = new JsonProjectsUnmarshaller();
        Unmarshaller<HttpResponse, Project> projectUnmarshaller = new JsonProjectUnmarshaller();
        TeamCity teamcity = new TeamCity(new Server(host), http, projectsUnmarshaller, projectUnmarshaller);

        Iterable<Project> projects = teamcity.retrieveProjects();
        Iterable<BuildType> buildTypes = teamcity.retrieveBuildTypes(projects);
        for (BuildType buildType : buildTypes) {
            Build build = teamcity.retrieveLatestBuild(buildType);
            System.out.printf("%s: #%s (id:%s) - %s%n", build.getBuildType().getName(), build.getNumber(), build.getId(), build.getStatusText());
        }
    }

    @Test
    public void shouldRetrieveProjects() throws MalformedURLException {
        context.checking(new Expectations() {{
            oneOf(http).get(new URL("http://example.com:8111/guestAuth/app/rest/projects"), headers(header("Accept", "application/json"))); will(returnValue(ok));
            oneOf(projects).unmarshall(ok); will(returnValue(expectedProjects));
        }});
        Iterable<Project> projects = teamcity.retrieveProjects();
        assertThat(projects, Matchers.<Iterable<Project>>is(expectedProjects));
    }

    @Test (expected = UnexpectedResponse.class)
    public void shouldHandleHttpErrorWhenRetrievingProjects() {
        context.checking(new Expectations() {{
            oneOf(http).get(with(any(URL.class)), with(any(Headers.class))); will(returnValue(error));
        }});
        teamcity.retrieveProjects();
    }


    private URL proxy() {
        return url("http://localhost:8888");
    }

}
