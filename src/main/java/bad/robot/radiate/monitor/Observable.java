package bad.robot.radiate.monitor;

import bad.robot.radiate.Activity;
import bad.robot.radiate.Status;

public interface Observable {

    boolean addObservers(Observer... observer);

    boolean removeObservers(Observer... observer);

    void notifyObservers(Status status);

    void notifyObservers(Activity activity);

    void notifyObservers(Exception exception);

    void notifyObservers(Information information);
}
