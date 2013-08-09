package bad.robot.radiate.teamcity;

import bad.robot.radiate.monitor.MonitoringTask;
import bad.robot.radiate.monitor.MonitoringTasksFactory;
import bad.robot.radiate.ui.SwingUi;
import com.googlecode.totallylazy.Callable1;

import java.util.List;

import static bad.robot.http.HttpClients.anApacheClient;
import static com.googlecode.totallylazy.Sequences.sequence;

public class MultiplePageTeamCityMonitoring implements MonitoringTasksFactory {

    private final SwingUi ui;
    private final TeamCityConfiguration configuration;

    public MultiplePageTeamCityMonitoring(SwingUi ui) {
        this.ui = ui;
        this.configuration = YmlConfiguration.loadOrDefault(new BootstrapTeamCity());
    }

    @Override
    public List<MonitoringTask> create() {
        return sequence(projectsFromTeamCity()).map(toTasks()).toList();
    }

    private Callable1<Project, MonitoringTask> toTasks() {
        return new Callable1<Project, MonitoringTask>() {
            @Override
            public MonitoringTask call(Project project) throws Exception {
                MonitoringTask task = new AggregatedProjectMonitor(project, configuration);
                task.addObserver(ui.createStatusPanel());
                return task;
            }
        };
    }

    private Iterable<Project> projectsFromTeamCity() {
        Server server = new Server(configuration.host(), configuration.port());
        TeamCity teamcity = new TeamCity(server, anApacheClient(), new JsonProjectsUnmarshaller(), new JsonProjectUnmarshaller(), new JsonBuildUnmarshaller());
        return configuration.filter(teamcity.retrieveProjects());
    }

}
