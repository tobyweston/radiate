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
        Monitor monitor = new Monitor(threadPool, new DemoMonitor(ui));
        monitor.beginMonitoring();
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
        private final SwingUi ui;

        public DemoMonitor(SwingUi ui) {
            this.ui = ui;
        }

        @Override
        public List<MonitoringTask> create() {
            ArrayList<MonitoringTask> tasks = new ArrayList<>();
            for (int i = 0; i < 30; i++)
                tasks.add(createMonitoringTask());
            return tasks;
        }

        private RandomStatus createMonitoringTask() {
            RandomStatus status = new RandomStatus();
            status.addObserver(ui.createStatusPanel());
            return status;
        }
    }
}
