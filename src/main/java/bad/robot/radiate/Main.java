package bad.robot.radiate;

import bad.robot.radiate.monitor.*;
import bad.robot.radiate.teamcity.AllProjectsAsSingleTask;
import bad.robot.radiate.ui.SwingUi;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import static bad.robot.radiate.Status.Unknown;
import static java.util.concurrent.Executors.newScheduledThreadPool;

public class Main {

    private static final ScheduledExecutorService threadPool = newScheduledThreadPool(5, new MonitoringThreadFactory());
    private static final LoggingObserver logger = new LoggingObserver();

    private static final MonitoringTasksFactory monitoring = new AllProjectsAsSingleTask();

    public static void main(String... args) {
        SwingUi ui = new SwingUi();
        Monitor monitor = new Monitor(threadPool);
        monitoring.addObservers(logger, ui);
        try {
            List<MonitoringTask> tasks = monitoring.create();
            for (MonitoringTask task : tasks)
                task.addObservers(ui.createStatusPanel(), ui, logger);
            monitor.beginMonitoring(tasks);
        } catch (Exception e) {
            transitionOutBusyIndicator(ui.createStatusPanel());
            monitoring.notifyObservers(e);
            monitoring.notifyObservers(restartRequired());
        }
        ui.start();
        addShutdown(monitor);
    }

    private static void transitionOutBusyIndicator(final Observer panel) {
        new Timer(2500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                panel.update(Unknown);
            }
        }).start();
    }

    private static Information restartRequired() {
        return new Information("Restart required");
    }

    private static void addShutdown(final Monitor monitor) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                monitor.shutdown();
            }
        });
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
