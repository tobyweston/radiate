package bad.robot.radiate.teamcity;

import bad.robot.http.Headers;
import bad.robot.http.HttpClient;
import bad.robot.http.HttpResponse;
import bad.robot.radiate.Unmarshaller;
import com.googlecode.totallylazy.Callable1;
import com.googlecode.totallylazy.Either;
import com.googlecode.totallylazy.Sequence;

import java.net.URL;

import static bad.robot.http.HeaderList.headers;
import static bad.robot.http.HeaderPair.header;
import static bad.robot.radiate.teamcity.BuildLocatorBuilder.latest;
import static bad.robot.radiate.teamcity.BuildLocatorBuilder.running;
import static bad.robot.radiate.teamcity.TeamCityEndpoint.projectsEndpoint;
import static com.googlecode.totallylazy.Monad.methods.sequenceE;
import static com.googlecode.totallylazy.Sequences.flatten;
import static com.googlecode.totallylazy.Sequences.sequence;

class TeamCity {

    private final Headers headers = headers(header("Accept", "application/json"));
    private final Server server;
    private final HttpClient http;
    private final Unmarshaller<HttpResponse, Iterable<Project>> projects;
    private final Unmarshaller<HttpResponse, Project> project;
    private final Unmarshaller<HttpResponse, Build> build;

    public TeamCity(Server server, HttpClient http, Unmarshaller<HttpResponse, Iterable<Project>> projects, Unmarshaller<HttpResponse, Project> project, Unmarshaller<HttpResponse, Build> build) {
        this.server = server;
        this.http = http;
        this.projects = projects;
        this.project = project;
        this.build = build;
    }

    public Iterable<Project> retrieveProjects() {
        URL url = server.urlFor(projectsEndpoint);
        HttpResponse response = http.get(url, headers);
        if (response.ok())
            return projects.unmarshall(response);
        throw new UnexpectedResponse(url, response);
    }

    public Iterable<BuildType> retrieveBuildTypes(Iterable<Project> projects) {
        Either<TeamCityException, Sequence<Project>> expanded = sequenceE(sequence(projects).mapConcurrently(expandingToFullProject()));
        if (expanded.isLeft())
            throw expanded.left();
        return flatten(expanded.right());
    }

    public Build retrieveLatestBuild(BuildType buildType) {
        URL url = server.urlFor(running(buildType));
        HttpResponse response = http.get(url, headers);
        if (response.ok())
            return build.unmarshall(response);
        if (response.getStatusCode() == 404)
            return retrieveBuild(latest(buildType));
        throw new UnexpectedResponse(url, response);
    }

    private Build retrieveBuild(BuildLocatorBuilder locator) {
        URL url = server.urlFor(locator);
        HttpResponse response = http.get(url, headers);
        if (response.ok())
            return build.unmarshall(response);
        if (response.getStatusCode() == 404)
            return new NoBuild();
        throw new UnexpectedResponse(url, response);
    }

    private Callable1<Project, Either<TeamCityException, Project>> expandingToFullProject() {
        return new Callable1<Project, Either<TeamCityException, Project>>() {
            @Override
            public Either<TeamCityException, Project> call(Project project) throws Exception {
                URL url = server.urlFor(project);
                HttpResponse response = http.get(url, headers);
                if (response.ok())
                    return Either.right(TeamCity.this.project.unmarshall(response));;
                return Either.<TeamCityException, Project>left(new UnexpectedResponse(url, response));
            }
        };
    }

}
