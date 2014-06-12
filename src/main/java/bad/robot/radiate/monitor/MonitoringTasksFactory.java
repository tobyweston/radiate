package bad.robot.radiate.monitor;

import bad.robot.radiate.teamcity.AllProjectsAsSingleTask;
import bad.robot.radiate.teamcity.AllProjectsOneTaskPerProject;

import java.util.List;

public interface MonitoringTasksFactory extends Observable {

    public List<MonitoringTask> create();

    /** Default mode */
    static MonitoringTasksFactory singleAggregate() {
        return new AllProjectsAsSingleTask();
    }

    /** Chessboard mode */
    static MonitoringTasksFactory multipleProjects() {
        return new AllProjectsOneTaskPerProject();
    }

   static MonitoringTasksFactory multipleBuildsDemo() {
        return new MultipleBuildsDemo();
    }

    static MonitoringTasksFactory demo() {
        return new Demo();
    }

    static MonitoringTasksFactory erroring() {
        return new Error();
    }

    static class Error extends ThreadSafeObservable implements MonitoringTasksFactory {
        public List<MonitoringTask> create() {
            throw new RuntimeException("An unrecoverable error occurred");
        }
    }

}
