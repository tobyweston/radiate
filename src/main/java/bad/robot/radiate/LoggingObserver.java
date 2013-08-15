package bad.robot.radiate;

import bad.robot.radiate.monitor.Observable;
import bad.robot.radiate.monitor.Observer;
import org.apache.log4j.Logger;

class LoggingObserver implements Observer {

    @Override
    public void update(Status status) {
        // nothing for now
    }

    @Override
    public void update(Observable sender, Exception exception) {
        Logger.getLogger(sender.getClass()).error(exception.getMessage(), exception);
    }
}
