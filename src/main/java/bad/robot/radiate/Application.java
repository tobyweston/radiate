package bad.robot.radiate;

import bad.robot.radiate.monitor.*;
import bad.robot.radiate.ui.ScreenModeFactory;
import bad.robot.radiate.ui.SwingUi;

import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.Executors.newScheduledThreadPool;

public class Application {

    private static final ScheduledExecutorService threadPool = newScheduledThreadPool(5, new MonitoringThreadFactory());

    private final LoggingObserver logger = new LoggingObserver();
    private final Monitor monitor = new ScheduledMonitor(threadPool);
    private MonitoringTasks monitoring;
    private SwingUi ui;

    public void start(MonitoringTasksFactory taskFactory, ScreenModeFactory screen) {
        ui = new SwingUi(screen);
        taskFactory.addObservers(logger, ui);
        monitoring = new MonitoringTasks(taskFactory, monitor);
        for (MonitoringTask monitor : monitoring) {
            monitor.addObservers(ui.createStatusPanels());
            monitor.addObservers(ui, logger);
        }
        monitoring.start();
        ui.start();
        addShutdownHook(new StopMonitoring(monitor));
    }

    public void stop() {
        for (MonitoringTask monitor : monitoring)
            monitor.removeAllObservers();
        monitoring.stop();
        ui.stop();
    }

    private void addShutdownHook(Thread thread) {
        Runtime.getRuntime().addShutdownHook(thread);
    }
}
