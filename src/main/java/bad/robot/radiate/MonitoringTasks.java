package bad.robot.radiate;

import bad.robot.radiate.monitor.Monitor;
import bad.robot.radiate.monitor.MonitoringTask;
import bad.robot.radiate.monitor.MonitoringTasksFactory;

import java.util.Iterator;
import java.util.concurrent.ScheduledFuture;

import static bad.robot.radiate.RestartRequired.restartRequired;
import static java.util.Collections.emptyList;

public class MonitoringTasks implements Iterable<MonitoringTask> {

    private final Monitor monitor;

    private Iterable<MonitoringTask> tasks = emptyList();
    private Iterable<ScheduledFuture<?>> scheduled = emptyList();

    public MonitoringTasks(MonitoringTasksFactory factory, Monitor monitor) {
        this.monitor = monitor;
        try {
            tasks = factory.create();
        } catch (Exception e) {
            factory.notifyObservers(e);
            factory.notifyObservers(restartRequired());
        }
    }

    public void start() {
        scheduled = monitor.start(tasks);
    }

    public void stop() {
        monitor.stop(scheduled);
    }

    @Override
    public Iterator<MonitoringTask> iterator() {
        return tasks.iterator();
    }
}
