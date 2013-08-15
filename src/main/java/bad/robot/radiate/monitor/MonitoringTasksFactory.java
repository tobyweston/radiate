package bad.robot.radiate.monitor;

import java.util.List;

public interface MonitoringTasksFactory extends Observable {
    public List<MonitoringTask> create();
}
