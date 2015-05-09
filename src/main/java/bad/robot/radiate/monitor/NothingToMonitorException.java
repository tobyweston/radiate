package bad.robot.radiate.monitor;

@Deprecated
public class NothingToMonitorException extends MonitoringException {

    public NothingToMonitorException() {
        super("Nothing found to monitor, check your configuration");
    }
}
