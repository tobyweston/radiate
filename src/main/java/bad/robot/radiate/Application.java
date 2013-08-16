package bad.robot.radiate;

import bad.robot.radiate.monitor.LoggingObserver;
import bad.robot.radiate.monitor.MonitoringTask;
import bad.robot.radiate.monitor.MonitoringTasksFactory;
import bad.robot.radiate.monitor.Observer;
import bad.robot.radiate.ui.SwingUi;

class Application {

    private final LoggingObserver logger = new LoggingObserver();
    private final MonitoringTasksFactory taskFactory = MonitoringTypes.multipleProjects();

    public void start() {
        SwingUi ui = new SwingUi();
        taskFactory.addObservers(logger, ui);
        MonitoringTasks monitoring = new MonitoringTasks(taskFactory);
        for (MonitoringTask monitor : monitoring) {
            Observer panel = ui.createStatusPanel();
            monitor.addObservers(panel, ui, logger);
        }
        monitoring.start();
        ui.start();
    }

}
