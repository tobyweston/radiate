package bad.robot.radiate;

import bad.robot.radiate.monitor.MonitoringTask;
import bad.robot.radiate.monitor.MonitoringTasksFactory;
import bad.robot.radiate.monitor.RandomStatus;
import bad.robot.radiate.monitor.ThreadSafeObservable;
import bad.robot.radiate.teamcity.AllProjectsAsSingleTask;
import bad.robot.radiate.teamcity.AllProjectsOneTaskPerProject;

import java.util.ArrayList;
import java.util.List;

public class MonitoringTypes {

    /** Default mode */
    public static MonitoringTasksFactory singleAggregate() {
        return new AllProjectsAsSingleTask();
    }

    /** Chessboard mode */
    public static MonitoringTasksFactory multipleProjects() {
        return new AllProjectsOneTaskPerProject();
    }

    public static MonitoringTasksFactory demo() {
        return new DemoMonitor();
    }

    public static MonitoringTasksFactory erroring() {
        return new Error();
    }

    private static class DemoMonitor extends ThreadSafeObservable implements MonitoringTasksFactory {
        @Override
        public List<MonitoringTask> create() {
            ArrayList<MonitoringTask> tasks = new ArrayList<>();
            for (int i = 0; i < 99; i++)
                tasks.add(new RandomStatus());
            return tasks;
        }
    }

    private static class Error extends ThreadSafeObservable implements MonitoringTasksFactory {
        @Override
        public List<MonitoringTask> create() {
            throw new RuntimeException("An unrecoverable error occurred");
        }
    }

}
