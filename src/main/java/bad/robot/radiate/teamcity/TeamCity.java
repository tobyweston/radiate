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
import static bad.robot.radiate.teamcity.TeamCityEndpoints.*;
import static com.googlecode.totallylazy.Monad.methods.sequenceE;
import static com.googlecode.totallylazy.Sequences.flatten;
import static com.googlecode.totallylazy.Sequences.sequence;

class TeamCity {

    private final Headers headers = headers(header("Accept", "application/json"));
    private final Server server;
    private final HttpClient http;
    private final Authorisation authorisation;
    private final Unmarshaller<HttpResponse, Iterable<Project>> projects;
    private final Unmarshaller<HttpResponse, Project> project;
    private final Unmarshaller<HttpResponse, Build> build;

    public TeamCity(Server server, Authorisation authorisation, HttpClient http, Unmarshaller<HttpResponse, Iterable<Project>> projects, Unmarshaller<HttpResponse, Project> project, Unmarshaller<HttpResponse, Build> build) {
        this.server = server;
        this.http = http;
        this.projects = projects;
        this.project = project;
        this.build = build;
        this.authorisation = authorisation;
    }

    public Iterable<Project> retrieveProjects() {
        URL url = server.urlFor(projectsEndpointFor(authorisation));
        HttpResponse response = http.get(url, headers);
        if (response.ok())
            return projects.unmarshall(response);
        throw new UnexpectedResponse(url, response);
    }

    public Iterable<Project> retrieveFullProjects(Iterable<Project> projects) {
        Either<TeamCityException, Sequence<Project>> expanded = sequenceE(sequence(projects).mapConcurrently(expandingToFullProject()));
        if (expanded.isLeft())
            throw expanded.left();
        return expanded.right();
    }

    public Iterable<BuildType> retrieveBuildTypes(Iterable<Project> projects) {
        return flatten(retrieveFullProjects(projects));
    }

    public Build retrieveLatestBuild(BuildType buildType) {
        URL url = server.urlFor(running(buildType), authorisation);
        HttpResponse response = http.get(url, headers);
        if (response.getStatusCode() == 404)
            return retrieveBuild(latest(buildType));
        if (response.ok())
            return build.unmarshall(response);
        throw new UnexpectedResponse(url, response);
    }

    private Build retrieveBuild(BuildLocatorBuilder locator) {
        URL url = server.urlFor(locator, authorisation);
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
                    return Either.right(TeamCity.this.project.unmarshall(response));
                ;
                return Either.<TeamCityException, Project>left(new UnexpectedResponse(url, response));
            }
        };
    }

    public static class Functions {
        public static Callable1<BuildType, Build> toBuild(final TeamCity teamcity) {
            return new Callable1<BuildType, Build>() {
                @Override
                public Build call(BuildType buildType) throws Exception {
                    return teamcity.retrieveLatestBuild(buildType);
                }
            };
        }
    }
}
