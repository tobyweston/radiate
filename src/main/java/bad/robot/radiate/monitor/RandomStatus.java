package bad.robot.radiate.monitor;

import bad.robot.radiate.Activity;
import bad.robot.radiate.Status;
import bad.robot.radiate.teamcity.Progress;

import java.security.SecureRandom;
import java.util.Random;

import static bad.robot.radiate.Activity.*;
import static bad.robot.radiate.Activity.Error;
import static bad.robot.radiate.Status.*;

public class RandomStatus extends ThreadSafeObservable implements MonitoringTask {

    private static final Random random = new SecureRandom();
    private static final Status[] statuses = new Status[]{Ok, Ok, Ok, Ok, Ok, Ok, Ok, Ok, Ok, Broken, Unknown};
    private static final Activity[] acvitities = new Activity[]{Busy, Error, Idle, Progressing};

    @Override
    public void run() {
        Status status = randomStatus();
        notifyObservers(randomState(), new Progress(0, 40));
        notifyObservers(status);
        if (status == Broken)
            notifyObservers(new RuntimeException("Example problem"));
    }

    private static Status randomStatus() {
        return statuses[random.nextInt(statuses.length)];
    }

    private static Activity randomState() {
        return acvitities[random.nextInt(acvitities.length)];
    }

    public String toString() {
        return "randomly passing build monitoring";
    }
}
