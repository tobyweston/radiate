package bad.robot.radiate.teamcity;

import bad.robot.radiate.monitor.MonitoringTask;
import bad.robot.radiate.monitor.MonitoringTasksFactory;
import bad.robot.radiate.ui.SwingUi;
import com.googlecode.totallylazy.Callable1;
import com.googlecode.totallylazy.Sequence;

import java.util.List;

import static com.googlecode.totallylazy.Sequences.sequence;

public class MultiplePageTeamCityMonitoring implements MonitoringTasksFactory {

    private final SwingUi ui;
    private final TeamCityConfiguration configuration;
    private final TeamCity teamcity;

    public MultiplePageTeamCityMonitoring(SwingUi ui) {
        this.ui = ui;
        this.teamcity = new BootstrapTeamCity();
        this.configuration = YmlConfiguration.loadOrDefault(teamcity);
    }

    @Override
    public List<MonitoringTask> create() {
        Sequence<Project> projects = sequence(configuration.filter(teamcity.retrieveProjects()));
        return projects.map(toTasks()).toList();
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

}
