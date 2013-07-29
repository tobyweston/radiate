package bad.robot.radiate;

import bad.robot.radiate.monitor.Monitor;
import bad.robot.radiate.monitor.MonitoringTask;
import bad.robot.radiate.monitor.MonitoringTasksFactory;
import bad.robot.radiate.monitor.MonitoringThreadFactory;
import bad.robot.radiate.teamcity.TeamcityMonitoringTask;
import bad.robot.radiate.ui.SwingGui;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import static com.googlecode.totallylazy.Lists.list;
import static java.util.concurrent.Executors.newScheduledThreadPool;

public class Main {

    private static final ScheduledExecutorService threadPool = newScheduledThreadPool(5, new MonitoringThreadFactory());

    public static void main(String... args) {
        SwingGui ui = new SwingGui();
        Monitor monitor = new Monitor(threadPool, new TeamCityMonitoring(ui));
        monitor.beginMonitoring();
        ui.start();
        System.out.println("Thanks for coming!");
//        monitor.shutdown();
    }

    private static class TeamCityMonitoring implements MonitoringTasksFactory {
        private final SwingGui ui;

        public TeamCityMonitoring(SwingGui ui) {
            this.ui = ui;
        }

        @Override
        public List<? extends MonitoringTask> create() {
            return list(new TeamcityMonitoringTask(ui));
        }
    }

}
