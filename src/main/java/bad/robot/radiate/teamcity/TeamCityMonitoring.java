package bad.robot.radiate.teamcity;

import bad.robot.radiate.monitor.MonitoringTask;
import bad.robot.radiate.monitor.MonitoringTasksFactory;
import bad.robot.radiate.ui.SwingUi;

import java.util.List;

import static java.util.Arrays.asList;

public class TeamCityMonitoring implements MonitoringTasksFactory {

    private final SwingUi ui;
    private final TeamCityConfiguration configuration;

    public TeamCityMonitoring(SwingUi ui) {
        this.ui = ui;
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
    public List<? extends MonitoringTask> create() {
        return asList(new TeamcityMonitoringTask(ui, configuration));
    }

}
