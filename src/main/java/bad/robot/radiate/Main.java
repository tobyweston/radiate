package bad.robot.radiate;

import bad.robot.radiate.monitor.*;
import bad.robot.radiate.ui.SwingUi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.Executors.newScheduledThreadPool;

public class Main {

    private static final ScheduledExecutorService threadPool = newScheduledThreadPool(5, new MonitoringThreadFactory());

    public static void main(String... args) {
        SwingUi ui = new SwingUi();
        Monitor monitor = new Monitor(threadPool);
        try {
            List<MonitoringTask> tasks = new Error().create();
            for (MonitoringTask task : tasks)
                task.addObserver(ui.createStatusPanel());
            monitor.beginMonitoring(tasks);
        } catch (Exception e) {
            ui.createStatusPanel();
            ui.update(e);
        }
        ui.start();
        addShutdown(monitor);
    }

    private static void addShutdown(final Monitor monitor) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                monitor.shutdown();
            }
        });
    }

    private static class DemoMonitor implements MonitoringTasksFactory {
        @Override
        public List<MonitoringTask> create() {
            ArrayList<MonitoringTask> tasks = new ArrayList<>();
            for (int i = 0; i < 99; i++)
                tasks.add(new RandomStatus());
            return tasks;
        }

    }

    private static class Error implements MonitoringTasksFactory {
        @Override
        public List<MonitoringTask> create() {
            throw new RuntimeException("An unknown error occurred");
        }

    }
}
