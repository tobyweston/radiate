package bad.robot.radiate;

import bad.robot.radiate.monitor.Monitor;
import bad.robot.radiate.monitor.MonitoringThreadFactory;
import bad.robot.radiate.teamcity.TeamCityMonitoring;
import bad.robot.radiate.ui.SwingUi;

import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.Executors.newScheduledThreadPool;

public class Main {

    private static final ScheduledExecutorService threadPool = newScheduledThreadPool(5, new MonitoringThreadFactory());

    public static void main(String... args) {
        SwingUi ui = new SwingUi();
        Monitor monitor = new Monitor(threadPool, new TeamCityMonitoring(ui));
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

}
