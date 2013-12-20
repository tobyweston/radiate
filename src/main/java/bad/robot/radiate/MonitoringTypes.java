package bad.robot.radiate;

import bad.robot.radiate.monitor.*;
import bad.robot.radiate.teamcity.AllProjectsAsSingleTask;
import bad.robot.radiate.teamcity.AllProjectsOneTaskPerProject;

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

    public static MonitoringTasksFactory multipleBuildsDemo() {
        return new MultipleBuildsDemo();
    }

    public static MonitoringTasksFactory demo() {
        return new Demo();
    }

    public static MonitoringTasksFactory erroring() {
        return new Error();
    }

    private static class Error extends ThreadSafeObservable implements MonitoringTasksFactory {
        @Override
        public List<MonitoringTask> create() {
            throw new RuntimeException("An unrecoverable error occurred");
        }
    }

}
