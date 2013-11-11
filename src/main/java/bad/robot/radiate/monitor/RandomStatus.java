package bad.robot.radiate.monitor;

import bad.robot.radiate.State;
import bad.robot.radiate.Status;

import java.security.SecureRandom;
import java.util.Random;

import static bad.robot.radiate.State.*;
import static bad.robot.radiate.Status.Broken;
import static bad.robot.radiate.Status.Ok;
import static bad.robot.radiate.Status.Unknown;

public class RandomStatus extends ThreadSafeObservable implements MonitoringTask {

    private static final Random random = new SecureRandom();
    private static final Status[] statuses = new Status[]{Ok, Ok, Ok, Ok, Ok, Ok, Ok, Ok, Ok, Broken, Unknown};
    private static final State[] states = new State[]{Busy, Error, Idle, Progressing};

    @Override
    public void run() {
        Status status = randomStatus();
        notifyObservers(randomState());
        notifyObservers(status);
        if (status == Broken)
            notifyObservers(new RuntimeException("Example problem"));
    }

    private static Status randomStatus() {
        return statuses[random.nextInt(statuses.length)];
    }

    private static State randomState() {
        return states[random.nextInt(states.length)];
    }

    public String toString() {
        return "randomly passing build monitoring";
    }
}
