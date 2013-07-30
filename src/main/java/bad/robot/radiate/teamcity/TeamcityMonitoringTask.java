package bad.robot.radiate.teamcity;

import bad.robot.http.HttpClient;
import bad.robot.radiate.Environment;
import bad.robot.radiate.Status;
import bad.robot.radiate.monitor.MonitoringTask;
import bad.robot.radiate.ui.Ui;
import com.googlecode.totallylazy.Callable1;

import static bad.robot.http.HttpClients.anApacheClient;
import static bad.robot.radiate.Status.Unknown;
import static bad.robot.radiate.teamcity.StatusAggregator.statusAggregator;
import static com.googlecode.totallylazy.Sequences.sequence;

public class TeamcityMonitoringTask implements MonitoringTask {

    private final Ui ui;

    public TeamcityMonitoringTask(Ui ui) {
        this.ui = ui;
    }

    @Override
    public Status call() throws Exception {
        try {
            String host = Environment.getEnvironmentVariable("teamcity.host");
            String port = Environment.getEnvironmentVariable("teamcity.port", "8111");
            Server server = new Server(host, Integer.valueOf(port));
            HttpClient http = anApacheClient();
            TeamCity teamcity = new TeamCity(server, http, new JsonProjectsUnmarshaller(), new JsonProjectUnmarshaller(), new JsonBuildUnmarshaller());
            Iterable<Project> projects = teamcity.retrieveProjects();
            Iterable<BuildType> buildTypes = teamcity.retrieveBuildTypes(projects);
            Iterable<Status> statuses = sequence(buildTypes).map(toStatuses(teamcity));
            Status status = statusAggregator(statuses).getStatus();
            ui.update(status);
            return status;
        } catch (Exception e) {
            ui.update(e);
            return Unknown;
        }
    }

    private static Callable1<BuildType, Status> toStatuses(final TeamCity teamcity) {
        return new Callable1<BuildType, Status>() {
            @Override
            public Status call(BuildType buildType) throws Exception {
                Build build = teamcity.retrieveLatestBuild(buildType);
                System.out.printf("%s: #%s (id:%s) - %s (%s) %s%n", build.getBuildType().getName(), build.getNumber(), build.getId(), build.getStatus(), build.getStatusText(), build.getBuildType().getProjectName());
                return build.getStatus();
            }
        };
    }

}
