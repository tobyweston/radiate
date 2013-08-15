package bad.robot.radiate.monitor;

import bad.robot.radiate.Status;

import java.security.SecureRandom;
import java.util.Random;

import static bad.robot.radiate.Status.Broken;
import static bad.robot.radiate.Status.Ok;

public class RandomStatus extends ThreadSafeObservable implements MonitoringTask {

    private final Random random = new SecureRandom();

    @Override
    public Status call() throws Exception {
        Status[] values = new Status[]{Ok, Ok, Ok, Ok, Ok, Ok, Ok, Ok, Ok, Broken};
        Status status = values[random.nextInt(values.length)];
        notifyObservers(status);
        if (status == Broken)
            notifyObservers(new RuntimeException("Example problem"));
        return status;
    }

    public String toString() {
        return "randomly passing build monitoring";
    }
}
