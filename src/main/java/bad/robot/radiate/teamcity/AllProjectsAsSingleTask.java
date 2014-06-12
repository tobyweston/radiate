package bad.robot.radiate.teamcity;

import bad.robot.radiate.monitor.MonitoringTask;
import bad.robot.radiate.monitor.MonitoringTasksFactory;
import bad.robot.radiate.monitor.ThreadSafeObservable;

import java.util.Arrays;
import java.util.List;

/** @see {@link bad.robot.radiate.monitor.MonitoringTasksFactory#singleAggregate()} */
public class AllProjectsAsSingleTask extends ThreadSafeObservable implements MonitoringTasksFactory {

    @Override
    public List<MonitoringTask> create() {
        TeamCityConfiguration configuration = YmlConfiguration.loadOrCreate(new BootstrapTeamCity(), this);
        return Arrays.<MonitoringTask>asList(new AllProjectsMonitor(configuration));
    }

    @Override
    public String toString() {
        return "multiple projects as a single aggregate";
    }
}
