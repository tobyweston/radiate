package bad.robot.radiate.monitor;

import java.util.List;
import java.util.concurrent.ScheduledFuture;

public interface Monitor {
    Iterable<ScheduledFuture<?>> start(Iterable<MonitoringTask> tasks);
    void stop(Iterable<ScheduledFuture<?>> tasks);
    List<Runnable> stop();
}
