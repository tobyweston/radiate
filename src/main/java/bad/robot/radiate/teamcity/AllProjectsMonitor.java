package bad.robot.radiate.teamcity;

import bad.robot.http.HttpClient;
import bad.robot.radiate.Aggregator;
import bad.robot.radiate.monitor.Information;
import bad.robot.radiate.monitor.MonitoringTask;
import bad.robot.radiate.monitor.NonRepeatingObservable;
import com.googlecode.totallylazy.Sequence;

import static bad.robot.radiate.Aggregator.aggregate;
import static bad.robot.radiate.Functions.asString;
import static com.googlecode.totallylazy.Sequences.sequence;
import static java.lang.String.format;

/** @see {@link bad.robot.radiate.MonitoringTypes#singleAggregate()} */
public class AllProjectsMonitor extends NonRepeatingObservable implements MonitoringTask {

    private final TeamCityConfiguration configuration;
    private final HttpClient http;
    private final Server server;
    private final TeamCity teamcity;

    private Sequence<String> monitored = sequence("unknown");

    public AllProjectsMonitor(TeamCityConfiguration configuration) {
        this.configuration = configuration;
        this.server = new Server(configuration.host(), configuration.port());
        this.http = new HttpClientFactory().create(configuration);
        this.teamcity = new TeamCity(server, configuration.authorisation(), http, new JsonProjectsUnmarshaller(), new JsonProjectUnmarshaller(), new JsonBuildUnmarshaller());
    }

    @Override
    public void run() {
        try {
            Iterable<Project> projects = configuration.filter(teamcity.retrieveProjects());
            monitored = sequence(projects).map(asString());
            Iterable<BuildType> buildTypes = teamcity.retrieveBuildTypes(projects);
            Sequence<Build> builds = sequence(buildTypes).mapConcurrently(buildType -> teamcity.retrieveLatestBuild(buildType));
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
        return format("monitoring %s as a single aggregate", monitored.toString("\r\n"));
    }
}
