package bad.robot.radiate.teamcity;

import bad.robot.radiate.monitor.MonitoringTask;
import bad.robot.radiate.monitor.MonitoringTasksFactory;
import bad.robot.radiate.monitor.Observer;

import java.util.List;

import static java.util.Arrays.asList;

public class TeamCityMonitoring implements MonitoringTasksFactory {

    private final Observer observer;
    private final TeamCityConfiguration configuration;

    public TeamCityMonitoring(Observer observer) {
        this.observer = observer;
        this.configuration = loadConfigurationOrDefault(new BootstrapTeamCity());
    }

    private static TeamCityConfiguration loadConfigurationOrDefault(TeamCity teamcity) {
        try {
            YmlConfigurationFile file = new YmlConfigurationFile();
            file.initialise(teamcity);
            return new YmlConfiguration(file);
        } catch (Exception e) {
            return new EnvironmentVariableConfiguration();
        }
    }

    @Override
    public List<MonitoringTask> create() {
        MonitoringTask task = new TeamcityMonitoringTask(configuration);
        task.addObserver(observer);
        return asList(task);
    }

}
