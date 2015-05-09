package bad.robot.radiate.monitor;

import bad.robot.radiate.Activity;
import bad.robot.radiate.Progress;
import bad.robot.radiate.Status;

import java.util.stream.Stream;

@Deprecated
public interface Observable {
    void addObservers(Stream<Observer> observer);
    boolean addObservers(Observer... observer);
    boolean removeObservers(Observer... observer);
    void removeAllObservers();
    void notifyObservers(Status status);
    void notifyObservers(Activity activity, Progress progress);
    void notifyObservers(Exception exception);
    void notifyObservers(Information information);
}
