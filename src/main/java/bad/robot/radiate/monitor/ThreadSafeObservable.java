package bad.robot.radiate.monitor;

import bad.robot.radiate.Status;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.Arrays.asList;

public class ThreadSafeObservable implements Observable {

    private final List<Observer> observers = new CopyOnWriteArrayList<>();

    @Override
    public boolean addObserver(Observer... observers) {
        return this.observers.addAll(asList(observers));
    }

    @Override
    public boolean removeObserver(Observer... observers) {
        return this.observers.removeAll(asList(observers));
    }

    @Override
    public void notifyObservers(Status status) {
        for (Observer observer : observers)
            observer.update(status);
    }

    @Override
    public void notifyObservers(Exception exception) {
        for (Observer observer : observers)
            observer.update(exception);
    }
}
