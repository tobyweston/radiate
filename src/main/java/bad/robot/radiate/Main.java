package bad.robot.radiate;

import static bad.robot.radiate.MonitoringTypes.singleAggregate;
import static bad.robot.radiate.ui.FrameFactory.fullScreen;

public class Main {

    public static final Application Radiate = new Application();

    public static void main(String... args) {
        Radiate.start(singleAggregate(), fullScreen());
    }

}
