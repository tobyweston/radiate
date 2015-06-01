package bad.robot.radiate.teamcity;

import bad.robot.radiate.monitor.MonitoringTask;
import bad.robot.radiate.monitor.MonitoringTasksFactory;
import bad.robot.radiate.monitor.ThreadSafeObservable;
import com.googlecode.totallylazy.Callable1;
import com.googlecode.totallylazy.Sequence;

import java.util.List;

import static bad.robot.radiate.teamcity.NonEmptyProject.nonEmpty;
import static com.googlecode.totallylazy.Sequences.sequence;

/** @see {@link bad.robot.radiate.monitor.MonitoringTasksFactory#multipleProjects} */
@Deprecated
public class AllProjectsOneTaskPerProject extends ThreadSafeObservable implements MonitoringTasksFactory {

    @Override
    public List<MonitoringTask> create() {
        TeamCityConfiguration configuration = YmlConfiguration.loadOrCreate(new BootstrapTeamCity(), this);
        TeamCity teamcity = createTeamCity(configuration);
        Sequence<Project> projects = sequence(configuration.filter(teamcity.retrieveProjects()));
        return sequence(teamcity.retrieveFullProjects(projects)).filter(nonEmpty()).map(toTasks(configuration)).toList();
    }

    private static TeamCity createTeamCity(TeamCityConfiguration configuration) {
        Server server = new Server(configuration.host(), configuration.port());
        return new TeamCity(server, configuration.authorisation(), new HttpClientFactory().create(configuration), new JsonProjectsUnmarshaller(), new JsonProjectUnmarshaller(), new JsonBuildUnmarshaller());
    }

    private static Callable1<Project, MonitoringTask> toTasks(TeamCityConfiguration configuration) {
        return project -> new SingleProjectMonitor(project, configuration);
    }

    @Override
    public String toString() {
        return "monitoring multiple projects";
    }
}
