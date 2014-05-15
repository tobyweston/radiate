package bad.robot.radiate.monitor;

import bad.robot.radiate.Activity;
import bad.robot.radiate.Progress;
import bad.robot.radiate.Status;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.Arrays.asList;

public class ThreadSafeObservable implements Observable {

    private final List<Observer> observers = new CopyOnWriteArrayList<>();

    @Override
    public boolean addObservers(Observer... observers) {
        return this.observers.addAll(asList(observers));
    }

    @Override
    public boolean removeObservers(Observer... observers) {
        return this.observers.removeAll(asList(observers));
    }

    @Override
    public void removeAllObservers() {
        observers.clear();
    }

    @Override
    public void notifyObservers(Status status) {
        for (Observer observer : observers)
            observer.update(this, status);
    }

    @Override
    public void notifyObservers(Exception exception) {
        for (Observer observer : observers)
            observer.update(this, exception);
    }

    @Override
    public void notifyObservers(Information information) {
        for (Observer observer : observers)
            observer.update(this, information);
    }

    @Override
    public void notifyObservers(Activity activity, Progress progress) {
        for (Observer observer : observers)
            observer.update(this, activity, progress);
    }
}
