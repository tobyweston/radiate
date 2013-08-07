package bad.robot.radiate.teamcity;

import bad.robot.http.Headers;
import bad.robot.http.HttpClient;
import bad.robot.http.HttpResponse;
import bad.robot.http.StringHttpResponse;
import bad.robot.radiate.Unmarshaller;
import org.hamcrest.Matchers;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.Rule;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static bad.robot.http.EmptyHeaders.emptyHeaders;
import static bad.robot.http.HeaderList.headers;
import static bad.robot.http.HeaderPair.header;
import static bad.robot.http.matchers.Matchers.containsPath;
import static com.googlecode.totallylazy.Sequences.*;
import static com.googlecode.totallylazy.matchers.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TeamCityTest {

    @Rule public Mockery context = new JUnit4Mockery() {{
        setThreadingPolicy(new Synchroniser());
    }};

    private final Headers accept = headers(header("Accept", "application/json"));
    private final HttpClient http = context.mock(HttpClient.class);

    private final HttpResponse ok = new StringHttpResponse(200, "OK", "", emptyHeaders());
    private final HttpResponse anotherOk = new StringHttpResponse(200, "OK", "", emptyHeaders());
    private final HttpResponse error = new StringHttpResponse(500, "Yuk", "", emptyHeaders());
    private final HttpResponse notFound = new StringHttpResponse(404, "Not Found", "", emptyHeaders());

    private final Iterable<Project> projects = Any.projects();

    private final Unmarshaller<HttpResponse, Iterable<Project>> projectsUnmarshaller = context.mock(Unmarshaller.class, "projects unmarshaller");
    private final Unmarshaller<HttpResponse, Project> projectUnmarshaller = context.mock(Unmarshaller.class, "project unmarshaller");
    private final Unmarshaller<HttpResponse, Build> buildUnmarshaller = context.mock(Unmarshaller.class, "build unmarshaller");
    private final TeamCity teamcity = new TeamCity(new Server("example.com", 8111), http, projectsUnmarshaller, projectUnmarshaller, buildUnmarshaller);

    @Test
    public void shouldRetrieveProjects() throws MalformedURLException {
        context.checking(new Expectations() {{
            oneOf(http).get(new URL("http://example.com:8111/guestAuth/app/rest/projects"), accept); will(returnValue(ok));
            oneOf(projectsUnmarshaller).unmarshall(ok); will(returnValue(projects));
        }});
        assertThat(teamcity.retrieveProjects(), Matchers.<Iterable<Project>>is(projects));
    }

    @Test (expected = UnexpectedResponse.class)
    public void shouldHandleHttpErrorWhenRetrievingProjects() {
        context.checking(new Expectations() {{
            oneOf(http).get(with(any(URL.class)), with(any(Headers.class))); will(returnValue(error));
        }});
        teamcity.retrieveProjects();
    }

    @Test
    public void shouldRetrieveBuildTypes() throws MalformedURLException {
        final BuildTypes buildTypes = new BuildTypes(Any.buildType());
        final BuildTypes anotherBuildTypes = new BuildTypes(Any.buildType());
        final Project project = Any.project(buildTypes);
        final Project anotherProject = Any.project(anotherBuildTypes);

        context.checking(new Expectations() {{
            oneOf(http).get(new URL("http://example.com:8111" + first(projects).getHref()), accept); will(returnValue(ok));
            oneOf(http).get(new URL("http://example.com:8111" + second(projects).getHref()), accept); will(returnValue(anotherOk));
            oneOf(projectUnmarshaller).unmarshall(ok); will(returnValue(project));
            oneOf(projectUnmarshaller).unmarshall(anotherOk); will(returnValue(anotherProject));
        }});

        Iterable<BuildType> actual = teamcity.retrieveBuildTypes(projects);
        assertThat(actual, Matchers.<Iterable<BuildType>>is(sequence(first(buildTypes), first(anotherBuildTypes))));
    }

    @Test (expected = UnexpectedResponse.class)
    public void shouldHandleHttpErrorWhenRetrievingBuildTypes() {
        context.checking(new Expectations() {{
            exactly(2).of(http).get(with(any(URL.class)), with(any(Headers.class))); will(returnValue(error));
        }});
        teamcity.retrieveBuildTypes(projects);
    }

    @Test
    public void shouldRetrieveLatestRunningBuild() throws MalformedURLException {
        final BuildType buildType = Any.buildType();
        final Build build = Any.runningBuild();
        context.checking(new Expectations() {{
            oneOf(http).get(new URL("http://example.com:8111/guestAuth/app/rest/builds/buildType:" + buildType.getId() + ",running:true"), accept); will(returnValue(ok));
            oneOf(buildUnmarshaller).unmarshall(ok); will(returnValue(build));
        }});
        assertThat(teamcity.retrieveLatestBuild(buildType), is(build));
    }

    @Test (expected = UnexpectedResponse.class)
    public void shouldHandleHttpErrorWhenRetrievingLatestRunningBuild() throws MalformedURLException {
        final BuildType buildType = Any.buildType();
        context.checking(new Expectations() {{
            oneOf(http).get(with(containsPath(buildType.getId() + ",running:true")), with(any(Headers.class))); will(returnValue(error));
        }});
        teamcity.retrieveLatestBuild(buildType);
    }

    @Test
    public void shouldRetrieveLatestLatestNonRunningBuild() throws MalformedURLException {
        final BuildType buildType = Any.buildType();
        final Build build = Any.build();
        context.checking(new Expectations() {{
            oneOf(http).get(with(containsPath("running:true")), with(any(Headers.class))); will(returnValue(notFound));
            oneOf(http).get(new URL("http://example.com:8111/guestAuth/app/rest/builds/buildType:" + buildType.getId()), accept); will(returnValue(ok));
            oneOf(buildUnmarshaller).unmarshall(ok); will(returnValue(build));
        }});
        assertThat(teamcity.retrieveLatestBuild(buildType), is(build));
    }

    @Test (expected = UnexpectedResponse.class)
    public void shouldHandleHttpErrorWhenRetrievingLatestNonRunningBuild() throws MalformedURLException {
        final BuildType buildType = Any.buildType();
        context.checking(new Expectations() {{
            oneOf(http).get(with(containsPath("running:true")), with(any(Headers.class))); will(returnValue(notFound));
            oneOf(http).get(new URL("http://example.com:8111/guestAuth/app/rest/builds/buildType:" + buildType.getId()), accept); will(returnValue(error));
        }});
        teamcity.retrieveLatestBuild(buildType);
    }

    @Test
    public void shouldHandleProjectsWithNoBuildHistory() throws MalformedURLException {
        final BuildType buildType = Any.buildType();
        context.checking(new Expectations() {{
            oneOf(http).get(with(containsPath("running:true")), with(any(Headers.class))); will(returnValue(notFound));
            oneOf(http).get(new URL("http://example.com:8111/guestAuth/app/rest/builds/buildType:" + buildType.getId()), accept); will(returnValue(notFound));
        }});
        assertThat(teamcity.retrieveLatestBuild(buildType), is(((Build) new NoBuild())));
    }

    private static class StubConfiguration implements TeamCityConfiguration {
        @Override
        public String host() {
            return "example.com";
        }

        @Override
        public Integer port() {
            return 8111;
        }

        @Override
        public Iterable<Project> filter(Iterable<Project> projects) {
            return projects;
        }
    }
}
