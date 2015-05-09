package bad.robot.radiate.monitor;

import bad.robot.radiate.teamcity.AllProjectsAsSingleTask;
import bad.robot.radiate.teamcity.AllProjectsOneTaskPerProject;

import java.util.List;

@Deprecated
public interface MonitoringTasksFactory extends Observable {

    List<MonitoringTask> create();

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

    class Error extends ThreadSafeObservable implements MonitoringTasksFactory {
        public List<MonitoringTask> create() {
            throw new RuntimeException("An unrecoverable error occurred");
        }
    }

}
