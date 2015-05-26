package bad.robot.radiate;

import bad.robot.radiate.monitor.Information;

@Deprecated
public class RestartRequired extends Information {

    public static RestartRequired restartRequired() {
        return new RestartRequired();
    }

    private RestartRequired() {
        super("Restart required");
    }
}
