package bad.robot.radiate.teamcity;

import bad.robot.http.HttpClient;
import bad.robot.radiate.Activity;
import bad.robot.radiate.Functions;
import bad.robot.radiate.Status;
import bad.robot.radiate.monitor.Information;
import bad.robot.radiate.monitor.MonitoringTask;
import bad.robot.radiate.monitor.NonRepeatingObservable;
import com.googlecode.totallylazy.Callable1;
import com.googlecode.totallylazy.Pair;
import com.googlecode.totallylazy.Sequence;

import static bad.robot.http.HttpClients.anApacheClient;
import static bad.robot.http.configuration.HttpTimeout.httpTimeout;
import static bad.robot.radiate.ActivityAggregator.aggregated;
import static bad.robot.radiate.Functions.asString;
import static bad.robot.radiate.StatusAggregator.aggregated;
import static com.google.code.tempusfugit.temporal.Duration.minutes;
import static com.googlecode.totallylazy.Pair.pair;
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
            Sequence<Pair<Status, Activity>> states = sequence(buildTypes).mapConcurrently(toStatusAndActivity(teamcity));
            Status status = aggregated(states.map(Functions.<Status, Activity>first())).getStatus();
            Activity activity = aggregated(states.map(Functions.<Status, Activity>second())).getStatus();
            notifyObservers(activity);
            notifyObservers(status);
            notifyObservers(new Information(toString()));
        } catch (Exception e) {
            notifyObservers(e);
        }
    }

    private Callable1<BuildType, Pair<Status, Activity>> toStatusAndActivity(final TeamCity teamcity) {
        return new Callable1<BuildType, Pair<Status, Activity>>() {
            @Override
            public Pair<Status,Activity> call(BuildType buildType) throws Exception {
                notifyObservers(new Information(AllProjectsMonitor.this.toString()));
                Build build = teamcity.retrieveLatestBuild(buildType);
                return pair(build.getStatus(), build.getActivity());
            }
        };
    }

    @Override
    public String toString() {
        return format("monitoring %s as a single aggregate", monitored.toString("\r\n"));
    }
}
