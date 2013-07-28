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
import static com.googlecode.totallylazy.Predicates.isLeft;
import static com.googlecode.totallylazy.Predicates.isRight;
import static com.googlecode.totallylazy.Sequences.sequence;

class TeamCity implements Monitor {

    private final Headers headers = headers(header("Accept", "application/json"));
    private final Server server;
    private final HttpClient http;
    private final Unmarshaller<HttpResponse, Iterable<Project>> projects;
    private final Unmarshaller<HttpResponse, Project> project;
    private final Unmarshaller<HttpResponse, Build> build = new JsonBuildUnmarshaller();

    public TeamCity(Server server, HttpClient http, Unmarshaller<HttpResponse, Iterable<Project>> projects, Unmarshaller<HttpResponse, Project> project) {
        this.server = server;
        this.http = http;
        this.projects = projects;
        this.project = project;
    }

    @Override
    public Iterable<Project> retrieveProjects() {
        URL url = server.urlFor(projectsEndpoint);
        HttpResponse response = http.get(url, headers);
        if (response.ok())
            return projects.unmarshall(response);
        throw new UnexpectedResponse(url, response);
    }

    @Override
    public Iterable<BuildType> retrieveBuildTypes(Iterable<Project> projects) {
        Sequence<Either<? extends TeamCityException, Project>> expanded = sequence(projects).map(expandingToFullProject());
        Sequence<TeamCityException> exceptions = expanded.filter(isLeft()).map(left());
        if (!exceptions.isEmpty())
            throw exceptions.head();
        return expanded.filter(isRight()).map(right()).flatMap(buildTypes());
    }

    @Override
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
        throw new UnexpectedResponse(url, response);
    }

    private Callable1<Project, Either<? extends TeamCityException, Project>> expandingToFullProject() {
        return new Callable1<Project, Either<? extends TeamCityException, Project>>() {
            @Override
            public Either<? extends TeamCityException, Project> call(Project project) throws Exception {
                URL url = server.urlFor(project);
                HttpResponse response = http.get(url, headers);
                if (response.ok())
                    return Either.<TeamCityException, Project>right(TeamCity.this.project.unmarshall(response));;
                return Either.left(new UnexpectedResponse(url, response));
            }
        };
    }

    private Callable1<Either<? extends TeamCityException, Project>, TeamCityException> left() {
        return new Callable1<Either<? extends TeamCityException, Project>, TeamCityException>() {
            @Override
            public TeamCityException call(Either<? extends TeamCityException, Project> project) throws Exception {
                return project.left();
            }
        };
    }

    private Callable1<Either<? extends TeamCityException, Project>, Project> right() {
        return new Callable1<Either<? extends TeamCityException, Project>, Project>() {
            @Override
            public Project call(Either<? extends TeamCityException, Project> project) throws Exception {
                return project.right();
            }
        };
    }

    private Callable1<Project, Iterable<? extends BuildType>> buildTypes() {
        return new Callable1<Project, Iterable<? extends BuildType>>() {
            @Override
            public Iterable<? extends BuildType> call(Project project) throws Exception {
                return project;
            }
        };
    }

}
