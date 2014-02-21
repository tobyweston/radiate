package bad.robot.radiate.teamcity;

import bad.robot.http.HttpClient;
import bad.robot.radiate.Aggregator;
import bad.robot.radiate.monitor.Information;
import bad.robot.radiate.monitor.MonitoringTask;
import bad.robot.radiate.monitor.NonRepeatingObservable;
import com.googlecode.totallylazy.Sequence;

import static bad.robot.radiate.Aggregator.aggregate;
import static bad.robot.radiate.teamcity.TeamCity.Functions.toBuild;
import static com.googlecode.totallylazy.Sequences.sequence;
import static java.lang.String.format;
import static java.util.Arrays.asList;

public class SingleProjectMonitor extends NonRepeatingObservable implements MonitoringTask {

    private final HttpClient http;
    private final Server server;
    private final TeamCity teamcity;
    private final Project project;

    public SingleProjectMonitor(Project project, TeamCityConfiguration configuration) {
        this.project = project;
        this.server = new Server(configuration.host(), configuration.port());
        this.http = new HttpClientFactory().create();
        this.teamcity = new TeamCity(server, http, new JsonProjectsUnmarshaller(), new JsonProjectUnmarshaller(), new JsonBuildUnmarshaller());
    }

    @Override
    public void run() {
        try {
            Iterable<BuildType> buildTypes = teamcity.retrieveBuildTypes(asList(project));
            Sequence<Build> builds = sequence(buildTypes).mapConcurrently(toBuild(teamcity));
            Aggregator aggregated = aggregate(builds);
            notifyObservers(aggregated.activity(), aggregated.progress());
            notifyObservers(aggregated.status());
            notifyObservers(new Information(toString()));
        } catch (Exception e) {
            notifyObservers(e);
        }
    }

    @Override
    public String toString() {
        return format("%s (%s)", project.getName(), project.getId());
    }

}
