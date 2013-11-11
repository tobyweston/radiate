package bad.robot.radiate.monitor;

import bad.robot.radiate.State;
import bad.robot.radiate.Status;

public interface Observer {
    void update(Observable source, Status status);
    void update(Observable source, State state);
    void update(Observable source, Information information);
    void update(Observable source, Exception exception);
}
