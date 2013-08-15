package bad.robot.radiate.teamcity;

import bad.robot.http.HttpClient;
import bad.robot.radiate.Status;
import bad.robot.radiate.monitor.Information;
import bad.robot.radiate.monitor.MonitoringTask;
import bad.robot.radiate.monitor.ThreadSafeObservable;
import com.googlecode.totallylazy.Callable1;
import com.googlecode.totallylazy.Sequence;

import static bad.robot.http.HttpClients.anApacheClient;
import static bad.robot.radiate.Status.Unknown;
import static bad.robot.radiate.StatusAggregator.aggregated;
import static com.googlecode.totallylazy.Sequences.sequence;
import static java.lang.String.format;

public class AllProjectsTeamCityMonitoringTask extends ThreadSafeObservable implements MonitoringTask {

    private final TeamCityConfiguration configuration;
    private final HttpClient http = anApacheClient();
    private final Server server;
    private final TeamCity teamcity;

    private Sequence<String> monitored = sequence("unknown");

    public AllProjectsTeamCityMonitoringTask(TeamCityConfiguration configuration) {
        this.configuration = configuration;
        this.server = new Server(configuration.host(), configuration.port());
        this.teamcity = new TeamCity(server, http, new JsonProjectsUnmarshaller(), new JsonProjectUnmarshaller(), new JsonBuildUnmarshaller());
    }

    @Override
    public Status call() throws Exception {
        try {
            Iterable<Project> projects = configuration.filter(teamcity.retrieveProjects());
            monitored = sequence(projects).map(asString());
            Iterable<BuildType> buildTypes = teamcity.retrieveBuildTypes(projects);
            Iterable<Status> statuses = sequence(buildTypes).mapConcurrently(toStatuses(teamcity));
            Status status = aggregated(statuses).getStatus();
            notifyObservers(status);
            return status;
        } catch (Exception e) {
            notifyObservers(e);
            return Unknown;
        }
    }

    private static Callable1<Project, String> asString() {
        return new Callable1<Project, String>() {
            @Override
            public String call(Project project) throws Exception {
                return project.toString();
            }
        };
    }

    private Callable1<BuildType, Status> toStatuses(final TeamCity teamcity) {
        return new Callable1<BuildType, Status>() {
            @Override
            public Status call(BuildType buildType) throws Exception {
                notifyObservers(new Information(AllProjectsTeamCityMonitoringTask.this.toString()));
                return teamcity.retrieveLatestBuild(buildType).getStatus();
            }
        };
    }

    @Override
    public String toString() {
        return format("monitoring %s projects as a single aggregate", monitored.toString(", "));
    }
}
