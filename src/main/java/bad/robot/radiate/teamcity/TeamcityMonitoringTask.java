package bad.robot.radiate.teamcity;

import bad.robot.http.CommonHttpClient;
import bad.robot.radiate.Status;
import bad.robot.radiate.monitor.MonitoringTask;
import bad.robot.radiate.ui.Ui;
import com.googlecode.totallylazy.Callable1;

import static bad.robot.http.HttpClients.anApacheClient;
import static bad.robot.radiate.Status.Unknown;
import static bad.robot.radiate.teamcity.StatusAggregator.aggregated;
import static com.googlecode.totallylazy.Sequences.sequence;

public class TeamcityMonitoringTask implements MonitoringTask {

    private final Ui ui;
    private final TeamCityConfiguration configuration;
    private final CommonHttpClient http = anApacheClient();
    private final Server server;
    private final TeamCity teamcity;

    public TeamcityMonitoringTask(Ui ui, TeamCityConfiguration configuration) {
        this.ui = ui;
        this.configuration = configuration;
        this.server = new Server(configuration.host(), configuration.port());
        this.teamcity = new TeamCity(server, http, new JsonProjectsUnmarshaller(), new JsonProjectUnmarshaller(), new JsonBuildUnmarshaller());
    }

    @Override
    public Status call() throws Exception {
        try {
            Iterable<Project> projects = configuration.filter(teamcity.retrieveProjects());
            Iterable<BuildType> buildTypes = teamcity.retrieveBuildTypes(projects);
            Iterable<Status> statuses = sequence(buildTypes).mapConcurrently(toStatuses(teamcity));
            Status status = aggregated(statuses).getStatus();
            ui.update(status);
            return status;
        } catch (Exception e) {
            ui.update(e);
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

}
