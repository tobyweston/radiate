package bad.robot.radiate.teamcity;

import bad.robot.http.CommonHttpClient;
import bad.robot.radiate.Environment;
import bad.robot.radiate.Status;
import bad.robot.radiate.monitor.MonitoringTask;
import com.googlecode.totallylazy.Callable1;

import static bad.robot.http.HttpClients.anApacheClient;
import static bad.robot.radiate.teamcity.StatusAggregator.statusAggregator;
import static com.googlecode.totallylazy.Sequences.sequence;

public class TeamcityMonitoringTask implements MonitoringTask {

    @Override
    public Status call() throws Exception {
        String host = Environment.getEnvironmentVariable("teamcity.host");

        CommonHttpClient http = anApacheClient();
        TeamCity teamcity = new TeamCity(new Server(host), http, new JsonProjectsUnmarshaller(), new JsonProjectUnmarshaller(), new JsonBuildUnmarshaller());

        Iterable<Project> projects = teamcity.retrieveProjects();
        Iterable<BuildType> buildTypes = teamcity.retrieveBuildTypes(projects);
        Iterable<Status> statuses = sequence(buildTypes).map(toStatuses(teamcity));
        return statusAggregator(statuses).getStatus();
    }

    private static Callable1<BuildType, Status> toStatuses(final TeamCity teamcity) {
        return new Callable1<BuildType, Status>() {
            @Override
            public Status call(BuildType buildType) throws Exception {
                return teamcity.retrieveLatestBuild(buildType).getStatus();
            }
        };
    }

}
