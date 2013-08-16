package bad.robot.radiate;

import bad.robot.radiate.monitor.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.Executors.newScheduledThreadPool;

public class MonitoringTasks implements Iterable<MonitoringTask> {

    private static final ScheduledExecutorService threadPool = newScheduledThreadPool(5, new MonitoringThreadFactory());

    private final Monitor monitor = new Monitor(threadPool);

    private List<MonitoringTask> tasks = new ArrayList<>();

    public MonitoringTasks(MonitoringTasksFactory factory) {
        try {
            tasks = factory.create();
            addShutdownHook();
        } catch (Exception e) {
            factory.notifyObservers(e);
            factory.notifyObservers(restartRequired());
        }
    }

    public void start() {
        monitor.start(tasks);
    }

    public void stop() {
        monitor.stop();
    }

    private static Information restartRequired() {
        return new Information("Restart required");
    }

    private void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                monitor.stop();
            }
        });
    }

    @Override
    public Iterator<MonitoringTask> iterator() {
        return tasks.iterator();
    }
}
