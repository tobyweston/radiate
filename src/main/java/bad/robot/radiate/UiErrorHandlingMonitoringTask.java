package bad.robot.radiate;

import bad.robot.radiate.monitor.MonitoringTask;
import bad.robot.radiate.ui.Ui;

class UiErrorHandlingMonitoringTask implements MonitoringTask {

    private final MonitoringTask task;
    private final Ui ui;

    public UiErrorHandlingMonitoringTask(Ui ui, MonitoringTask task) {
        this.ui = ui;
        this.task = task;
    }

    @Override
    public Status call() throws Exception {
        try {
            return task.call();
        } catch (Exception e) {
            ui.update(e);
            throw e;
        }
    }
}
