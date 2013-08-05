package bad.robot.radiate;

import bad.robot.radiate.monitor.Monitor;
import bad.robot.radiate.monitor.MonitoringTask;
import bad.robot.radiate.monitor.MonitoringTasksFactory;
import bad.robot.radiate.monitor.MonitoringThreadFactory;
import bad.robot.radiate.teamcity.Server;
import bad.robot.radiate.teamcity.TeamcityConfiguration;
import bad.robot.radiate.teamcity.TeamcityMonitoringTask;
import bad.robot.radiate.teamcity.YmlConfiguration;
import bad.robot.radiate.ui.SwingUi;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import static com.googlecode.totallylazy.Lists.list;
import static java.util.concurrent.Executors.newScheduledThreadPool;

public class Main {

    private static final ScheduledExecutorService threadPool = newScheduledThreadPool(5, new MonitoringThreadFactory());

    public static void main(String... args) throws IOException {
        TeamcityConfiguration configuration = new YmlConfiguration();
        SwingUi ui = new SwingUi();
        Monitor monitor = new Monitor(threadPool, new TeamCityMonitoring(ui, new Server(configuration)));
        monitor.beginMonitoring();
        ui.start();
//        monitor.shutdown();
    }

    private static class TeamCityMonitoring implements MonitoringTasksFactory {
        private final SwingUi ui;
        private final Server server;

        public TeamCityMonitoring(SwingUi ui, Server server) {
            this.server = server;
            this.ui = ui;
        }

        @Override
        public List<? extends MonitoringTask> create() {
            return list(new TeamcityMonitoringTask(ui, server));
        }

    }

}
