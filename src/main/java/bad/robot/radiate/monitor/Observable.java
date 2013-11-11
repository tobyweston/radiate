package bad.robot.radiate.monitor;

import bad.robot.radiate.State;
import bad.robot.radiate.Status;

public interface Observable {

    boolean addObservers(Observer... observer);

    boolean removeObservers(Observer... observer);

    void notifyObservers(Status status);

    void notifyObservers(State state);

    void notifyObservers(Exception exception);

    void notifyObservers(Information information);
}
