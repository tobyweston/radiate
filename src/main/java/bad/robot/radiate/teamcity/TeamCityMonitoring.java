package bad.robot.radiate.teamcity;

import bad.robot.radiate.monitor.MonitoringTask;
import bad.robot.radiate.monitor.MonitoringTasksFactory;

import java.util.Arrays;
import java.util.List;

public class TeamCityMonitoring implements MonitoringTasksFactory {

    private final TeamCityConfiguration configuration;

    public TeamCityMonitoring() {
        this.configuration = YmlConfiguration.loadOrDefault(new BootstrapTeamCity());
    }

    @Override
    public List<MonitoringTask> create() {
        return Arrays.<MonitoringTask>asList(new TeamcityMonitoringTask(configuration));
    }

}
