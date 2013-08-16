package bad.robot.radiate.monitor;

import java.util.HashSet;
import java.util.Set;

public class NonInformationRepeatingObservable extends ThreadSafeObservable {

    private final Set<Information> previous = new HashSet<>();

    @Override
    public void notifyObservers(Information information) {
        if (previous.add(information)) {
            super.notifyObservers(information);
    }
}
}
