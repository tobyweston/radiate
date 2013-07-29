package bad.robot.radiate.monitor;

import java.util.List;

public interface MonitoringTasksFactory {
    public List<? extends MonitoringTask> create();
}
