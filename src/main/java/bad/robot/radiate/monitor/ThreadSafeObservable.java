package bad.robot.radiate.monitor;

import bad.robot.radiate.Status;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ThreadSafeObservable implements Observable {

    private final List<Observer> observers = new CopyOnWriteArrayList<>();

    @Override
    public boolean addObserver(Observer observer) {
        return observers.add(observer);
    }

    @Override
    public boolean removeObserver(Observer observer) {
        return observers.remove(observer);
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
