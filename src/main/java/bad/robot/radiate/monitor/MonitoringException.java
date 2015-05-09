package bad.robot.radiate.monitor;

@Deprecated
public abstract class MonitoringException extends RuntimeException {

    public MonitoringException(String message) {
        super(message);
    }
}
