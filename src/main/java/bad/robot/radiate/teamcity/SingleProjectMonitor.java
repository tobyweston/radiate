package bad.robot.radiate.teamcity;

import bad.robot.http.HttpClient;
import bad.robot.radiate.Status;
import bad.robot.radiate.monitor.Information;
import bad.robot.radiate.monitor.MonitoringTask;
import bad.robot.radiate.monitor.NonInformationRepeatingObservable;
import com.googlecode.totallylazy.Callable1;

import static bad.robot.http.HttpClients.anApacheClient;
import static bad.robot.http.configuration.HttpTimeout.httpTimeout;
import static bad.robot.radiate.Status.Unknown;
import static bad.robot.radiate.StatusAggregator.aggregated;
import static com.google.code.tempusfugit.temporal.Duration.minutes;
import static com.googlecode.totallylazy.Sequences.sequence;
import static java.lang.String.format;
import static java.util.Arrays.asList;

public class SingleProjectMonitor extends NonInformationRepeatingObservable implements MonitoringTask {

    private final HttpClient http = anApacheClient().with(httpTimeout(minutes(1)));
    private final Server server;
    private final TeamCity teamcity;
    private final Project project;

    public SingleProjectMonitor(Project project, TeamCityConfiguration configuration) {
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
            notifyObservers(new Information(toString()));
            return status;
        } catch (Exception e) {
            notifyObservers(e);
            return Unknown;
        }
    }

    private Callable1<BuildType, Status> toStatuses(final TeamCity teamcity) {
        return new Callable1<BuildType, Status>() {
            @Override
            public Status call(BuildType buildType) throws Exception {
                return teamcity.retrieveLatestBuild(buildType).getStatus();
            }
        };
    }

    @Override
    public String toString() {
        return format("%s (%s)", project.getName(), project.getId());
    }

}
