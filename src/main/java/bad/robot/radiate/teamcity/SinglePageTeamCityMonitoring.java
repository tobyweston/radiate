package bad.robot.radiate.teamcity;

import bad.robot.radiate.monitor.MonitoringTask;
import bad.robot.radiate.monitor.MonitoringTasksFactory;
import bad.robot.radiate.ui.SwingUi;

import java.util.List;

import static java.util.Arrays.asList;

public class SinglePageTeamCityMonitoring implements MonitoringTasksFactory {

    private final SwingUi ui;
    private final TeamCityConfiguration configuration;

    public SinglePageTeamCityMonitoring(SwingUi ui) {
        this.ui = ui;
        this.configuration = YmlConfiguration.loadOrDefault(new BootstrapTeamCity());
    }

    @Override
    public List<MonitoringTask> create() {
        MonitoringTask task = new TeamcityMonitoringTask(configuration);
        task.addObserver(ui.createStatusPanel());
        return asList(task);
    }

}
