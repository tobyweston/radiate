package bad.robot.radiate.monitor;

import bad.robot.radiate.Status;

import java.security.SecureRandom;
import java.util.Random;

public class RandomStatus extends ThreadSafeObservable implements MonitoringTask {

    private final Random random = new SecureRandom();

    @Override
    public Status call() throws Exception {
        Status[] values = Status.values();
        Status status = values[random.nextInt(values.length)];
        notifyObservers(status);
        return status;
    }
}
