package bad.robot.radiate.monitor;

import bad.robot.radiate.Status;

import java.util.concurrent.Callable;

public interface MonitoringTask extends Callable<Status>, Observable {
}
