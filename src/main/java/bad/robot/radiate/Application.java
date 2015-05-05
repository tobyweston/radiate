package bad.robot.radiate;

import bad.robot.radiate.monitor.*;
import bad.robot.radiate.ui.FrameFactory;
import bad.robot.radiate.ui.SwingUi;

import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.Executors.newScheduledThreadPool;

public class Application {

    private static final ScheduledExecutorService threadPool = newScheduledThreadPool(5, new MonitoringThreadFactory());

    private final LoggingObserver logger = new LoggingObserver();
    private final Monitor monitor = new ScheduledMonitor(threadPool);

    private MonitoringTasksFactory currentTasks;
    private FrameFactory currentFrames;
    private MonitoringTasks monitoring;
    private SwingUi ui;

    public void start(MonitoringTasksFactory tasks, FrameFactory frames) {
        currentTasks = tasks;
        currentFrames = frames;
        ui = new SwingUi(frames);
        tasks.addObservers(logger, ui);
        monitoring = new MonitoringTasks(tasks, monitor);
        for (MonitoringTask monitor : monitoring) {
            monitor.addObservers(ui.createStatusPanels());
            monitor.addObservers(ui, logger);
        }
        monitoring.start();
        ui.start();
        addShutdownHook();
    }

    public void stop() {
        for (MonitoringTask monitor : monitoring)
            monitor.removeAllObservers();
        currentTasks.removeAllObservers();
        monitoring.stop();
        ui.stop();
    }

    private void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(monitor::stop));
    }

    public FrameFactory getCurrentFrames() {
        return currentFrames;
    }

    public MonitoringTasksFactory getCurrentTasks() {
        return currentTasks;
    }
}
