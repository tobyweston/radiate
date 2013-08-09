package bad.robot.radiate.monitor;

import bad.robot.radiate.Status;

public interface Observable {

    boolean addObserver(Observer observer);

    boolean removeObserver(Observer observer);

    void notifyObservers(Status status);

    void notifyObservers(Exception exception);
}
