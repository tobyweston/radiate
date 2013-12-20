package bad.robot.radiate;

import bad.robot.radiate.monitor.MonitoringTask;
import bad.robot.radiate.monitor.MonitoringTasksFactory;
import bad.robot.radiate.monitor.RandomStatus;
import bad.robot.radiate.monitor.ThreadSafeObservable;

import java.util.ArrayList;
import java.util.List;

class MultipleBuildsDemo extends ThreadSafeObservable implements MonitoringTasksFactory {

    public static final int builds = 4 * 4;

    @Override
    public List<MonitoringTask> create() {
        ArrayList<MonitoringTask> tasks = new ArrayList<>();
        for (int i = 0; i < builds; i++)
            tasks.add(new RandomStatus());
        return tasks;
    }
}
