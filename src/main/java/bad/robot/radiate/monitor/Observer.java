package bad.robot.radiate.monitor;

import bad.robot.radiate.Status;

public interface Observer {
    void update(Status status);
    void update(Exception exception);
}
