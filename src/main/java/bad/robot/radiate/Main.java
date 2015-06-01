package bad.robot.radiate;

import static bad.robot.radiate.monitor.MonitoringTasksFactory.singleAggregate;
import static bad.robot.radiate.ui.FrameFactory.fullScreen;

@Deprecated
public class Main {

    public static final Application Radiate = new Application();

    public static void main(String... args) {
        Radiate.start(singleAggregate(), fullScreen());
    }

}
