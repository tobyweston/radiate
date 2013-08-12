package bad.robot.radiate.teamcity;

import bad.robot.http.HttpClient;
import bad.robot.radiate.Status;
import bad.robot.radiate.monitor.MonitoringTask;
import bad.robot.radiate.monitor.ThreadSafeObservable;
import com.googlecode.totallylazy.Callable1;

import static bad.robot.http.HttpClients.anApacheClient;
import static bad.robot.radiate.Status.Unknown;
import static bad.robot.radiate.StatusAggregator.aggregated;
import static com.googlecode.totallylazy.Sequences.sequence;
import static java.lang.String.format;
import static java.util.Arrays.asList;

public class AggregatedProjectMonitor extends ThreadSafeObservable implements MonitoringTask {

    private final HttpClient http = anApacheClient();
    private final Server server;
    private final TeamCity teamcity;
    private final Project project;

    public AggregatedProjectMonitor(Project project, TeamCityConfiguration configuration) {
        this.project = project;
        this.server = new Server(configuration.host(), configuration.port());
        this.teamcity = new TeamCity(server, http, new JsonProjectsUnmarshaller(), new JsonProjectUnmarshaller(), new JsonBuildUnmarshaller());
    }

    @Override
    public Status call() throws Exception {
        try {
            Iterable<BuildType> buildTypes = teamcity.retrieveBuildTypes(asList(project));
            Iterable<Status> statuses = sequence(buildTypes).mapConcurrently(toStatuses(teamcity));
            Status status = aggregated(statuses).getStatus();
            notifyObservers(status);
            return status;
        } catch (Exception e) {
            notifyObservers(e);
            e.printStackTrace(System.err);
            return Unknown;
        }
    }

    private static Callable1<BuildType, Status> toStatuses(final TeamCity teamcity) {
        return new Callable1<BuildType, Status>() {
            @Override
            public Status call(BuildType buildType) throws Exception {
                Build build = teamcity.retrieveLatestBuild(buildType);
                System.out.printf("%s: #%s (id:%s) - %s (%s) %s %n", build.getBuildType().getName(), build.getNumber(), build.getId(), build.getStatus(), build.getStatusText(), build.getBuildType().getProjectName());
                return build.getStatus();
            }
        };
    }

    @Override
    public String toString() {
        return format("monitoring %s (%s)", project.getName(), project.getId());
    }

}
