package bad.robot.radiate.monitor;

public class StopMonitoring extends Thread {

    private final Monitor monitor;

    public StopMonitoring(Monitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        monitor.stop();
    }
}
