package bad.robot.radiate.monitor;

import bad.robot.radiate.Activity;
import bad.robot.radiate.Progress;
import bad.robot.radiate.Status;

@Deprecated
public interface Observer {
    default void update(Observable source, Status status) { /* ignore status updates */ }
    default void update(Observable source, Activity activity, Progress progress) { /* ignore status updates */ }
    default void update(Observable source, Information information) { /* ignore status updates */ }
    default void update(Observable source, Exception exception) { /* ignore status updates */ }
}
