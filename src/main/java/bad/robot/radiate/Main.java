package bad.robot.radiate;

import bad.robot.radiate.monitor.Monitor;
import bad.robot.radiate.monitor.MonitoringTask;
import bad.robot.radiate.monitor.MonitoringTasksFactory;
import bad.robot.radiate.monitor.MonitoringThreadFactory;
import bad.robot.radiate.teamcity.TeamcityMonitoringTask;
import bad.robot.radiate.ui.SwingUi;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import static com.googlecode.totallylazy.Lists.list;
import static java.util.concurrent.Executors.newScheduledThreadPool;

public class Main {

    private static final ScheduledExecutorService threadPool = newScheduledThreadPool(5, new MonitoringThreadFactory());

    public static void main(String... args) {
        SwingUi ui = new SwingUi();
        Monitor monitor = new Monitor(threadPool, new TeamCityMonitoring(ui));
        monitor.beginMonitoring();
        ui.start();
//        monitor.shutdown();
    }

    private static class TeamCityMonitoring implements MonitoringTasksFactory {
        private final SwingUi ui;

        public TeamCityMonitoring(SwingUi ui) {
            this.ui = ui;
        }

        @Override
        public List<? extends MonitoringTask> create() {
            return list(new TeamcityMonitoringTask(ui));
        }
    }

}
