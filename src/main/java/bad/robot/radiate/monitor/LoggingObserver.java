package bad.robot.radiate.monitor;

import bad.robot.radiate.Status;
import org.apache.log4j.Logger;

public class LoggingObserver implements Observer {

    @Override
    public void update(Status status) {
        // nothing for now
    }

    @Override
    public void update(Observable observable, Exception exception) {
        Logger.getLogger(observable.getClass()).error(exception.getMessage(), exception);
    }

    @Override
    public void update(Observable observable, Information information) {
        Logger.getLogger(observable.getClass()).info(information);
    }
}
