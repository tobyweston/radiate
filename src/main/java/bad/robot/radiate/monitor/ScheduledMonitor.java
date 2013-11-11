package bad.robot.radiate.monitor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

public class ScheduledMonitor implements Monitor {

    private final ScheduledExecutorService executor;
    private final int frequency = 30;

    public ScheduledMonitor(ScheduledExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public Iterable<ScheduledFuture<?>> start(Iterable<MonitoringTask> tasks) {
        List<ScheduledFuture<?>> scheduled = new ArrayList<>();
        for (MonitoringTask task : tasks)
            scheduled.add(executor.scheduleWithFixedDelay(task, 0, frequency, SECONDS));
        return scheduled;
    }

    @Override
    public void stop(Iterable<ScheduledFuture<?>> tasks) {
        for (ScheduledFuture<?> task : tasks)
            task.cancel(true);
    }

    @Override
    public List<Runnable> stop() {
        List<Runnable> tasks = executor.shutdownNow();
        for (Runnable task : tasks)
            ((ScheduledFuture<?>) task).cancel(true);
        return tasks;
    }
}
