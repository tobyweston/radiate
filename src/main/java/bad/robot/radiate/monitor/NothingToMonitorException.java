package bad.robot.radiate.monitor;

public class NothingToMonitorException extends MonitoringException {

    public NothingToMonitorException() {
        super("Nothing found to monitor, check your configuration");
    }
}
