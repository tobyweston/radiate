package bad.robot.radiate.teamcity;

import bad.robot.radiate.monitor.MonitoringTask;
import bad.robot.radiate.monitor.MonitoringTasksFactory;
import bad.robot.radiate.monitor.ThreadSafeObservable;

import java.util.Arrays;
import java.util.List;

public class AllProjectsTeamCityMonitoring extends ThreadSafeObservable implements MonitoringTasksFactory {

    @Override
    public List<MonitoringTask> create() {
        TeamCityConfiguration configuration = YmlConfiguration.loadOrDefault(new BootstrapTeamCity(), this);
        return Arrays.<MonitoringTask>asList(new AllProjectsTeamCityMonitoringTask(configuration));
    }

}
