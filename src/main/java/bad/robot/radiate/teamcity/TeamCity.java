package bad.robot.radiate.teamcity;

import bad.robot.http.Headers;
import bad.robot.http.HttpClient;
import bad.robot.http.HttpResponse;
import bad.robot.radiate.Unmarshaller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static bad.robot.http.HeaderList.headers;
import static bad.robot.http.HeaderPair.header;
import static bad.robot.radiate.teamcity.TeamCityEndpoint.projectsEndpoint;

class TeamCity {

    private final Headers headers = headers(header("Accept", "application/json"));
    private final Server server;
    private final HttpClient http;
    private final Unmarshaller<HttpResponse, Iterable<Project>> projects;
    private final Unmarshaller<HttpResponse, Project> project;

    public TeamCity(Server server, HttpClient http, Unmarshaller<HttpResponse, Iterable<Project>> projects, Unmarshaller<HttpResponse, Project> project) {
        this.server = server;
        this.http = http;
        this.projects = projects;
        this.project = project;
    }

    public Iterable<Project> retrieveProjects() {
        URL url = server.urlFor(projectsEndpoint);
        HttpResponse response = http.get(url, headers);
        if (response.ok())
            return projects.unmarshall(response);
        throw new UnexpectedResponse(url, response);
    }

    public Iterable<BuildType> retrieveBuildTypes(Iterable<Project> projects) {
        List<BuildType> buildTypes = new ArrayList<BuildType>();
        for (Project project : projects) {
            URL url = server.urlFor(project);
            HttpResponse response = http.get(url, headers);
            if (response.ok()) {
                for (BuildType buildType : this.project.unmarshall(response))
                    buildTypes.add(buildType);
            } else
                throw new UnexpectedResponse(url, response);
        }
        return buildTypes;
    }

}
