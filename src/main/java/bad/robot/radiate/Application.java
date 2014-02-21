package bad.robot.radiate;

import bad.robot.radiate.monitor.*;
import bad.robot.radiate.ui.SwingUi;

import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.Executors.newScheduledThreadPool;

class Application {

    private static final ScheduledExecutorService threadPool = newScheduledThreadPool(5, new MonitoringThreadFactory());

    private final LoggingObserver logger = new LoggingObserver();
    private final MonitoringTasksFactory taskFactory = MonitoringTypes.singleAggregate();
    private final Monitor monitor = new ScheduledMonitor(threadPool);

    public void start() {
        SwingUi ui = new SwingUi();
        taskFactory.addObservers(logger, ui);
        MonitoringTasks monitoring = new MonitoringTasks(taskFactory, monitor);
        for (MonitoringTask monitor : monitoring) {
            Observer panel = ui.createStatusPanel();
            monitor.addObservers(panel, ui, logger);
        }
        monitoring.start();
        ui.start();
        addShutdownHook(new StopMonitoring(monitor));
    }

    private void addShutdownHook(Thread thread) {
        Runtime.getRuntime().addShutdownHook(thread);
    }

}
