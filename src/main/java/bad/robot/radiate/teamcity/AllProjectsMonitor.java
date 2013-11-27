package bad.robot.radiate.teamcity;

import bad.robot.http.HttpClient;
import bad.robot.radiate.Aggregator;
import bad.robot.radiate.Progress;
import bad.robot.radiate.monitor.Information;
import bad.robot.radiate.monitor.MonitoringTask;
import bad.robot.radiate.monitor.NonRepeatingObservable;
import com.googlecode.totallylazy.Sequence;

import static bad.robot.http.HttpClients.anApacheClient;
import static bad.robot.http.configuration.HttpTimeout.httpTimeout;
import static bad.robot.radiate.Aggregator.aggregate;
import static bad.robot.radiate.Functions.asString;
import static bad.robot.radiate.teamcity.TeamCity.Functions.toBuild;
import static com.google.code.tempusfugit.temporal.Duration.minutes;
import static com.googlecode.totallylazy.Sequences.sequence;
import static java.lang.String.format;

/** @see {@link bad.robot.radiate.MonitoringTypes#singleAggregate()} */
public class AllProjectsMonitor extends NonRepeatingObservable implements MonitoringTask {

    private final TeamCityConfiguration configuration;
    private final HttpClient http = anApacheClient().with(httpTimeout(minutes(1)));
    private final Server server;
    private final TeamCity teamcity;

    private Sequence<String> monitored = sequence("unknown");

    public AllProjectsMonitor(TeamCityConfiguration configuration) {
        this.configuration = configuration;
        this.server = new Server(configuration.host(), configuration.port());
        this.teamcity = new TeamCity(server, http, new JsonProjectsUnmarshaller(), new JsonProjectUnmarshaller(), new JsonBuildUnmarshaller());
    }

    @Override
    public void run() {
        try {
            Iterable<Project> projects = configuration.filter(teamcity.retrieveProjects());
            monitored = sequence(projects).map(asString());
            Iterable<BuildType> buildTypes = teamcity.retrieveBuildTypes(projects);
            Sequence<Build> builds = sequence(buildTypes).mapConcurrently(toBuild(teamcity));
            Aggregator aggregated = aggregate(builds);
            Progress progress = aggregated.progress();
            System.out.println("progress = " + progress);
            notifyObservers(aggregated.activity(), progress);
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
