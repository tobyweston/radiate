package bad.robot.radiate.monitor;

import java.util.List;
import java.util.concurrent.ScheduledFuture;

@Deprecated
public interface Monitor {
    Iterable<ScheduledFuture<?>> start(Iterable<MonitoringTask> tasks);
    void cancel(Iterable<ScheduledFuture<?>> tasks);
    List<Runnable> stop();
}
