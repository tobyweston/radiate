package bad.robot.radiate.monitor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static com.google.code.tempusfugit.concurrency.CallableAdapter.runnable;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Monitor {

    private final ScheduledExecutorService executor;
    private final int frequency = 30;

    public Monitor(ScheduledExecutorService executor) {
        this.executor = executor;
    }

    public Iterable<ScheduledFuture<?>> start(Iterable<MonitoringTask> tasks) {
        List<ScheduledFuture<?>> scheduled = new ArrayList<>();
        for (MonitoringTask task : tasks)
            scheduled.add(executor.scheduleWithFixedDelay(runnable(task), 0, frequency, SECONDS));
        return scheduled;
    }

    public void stop(Iterable<ScheduledFuture<?>> tasks) {
        for (ScheduledFuture<?> task : tasks)
            task.cancel(true);
    }

    public List<Runnable> stop() {
        List<Runnable> tasks = executor.shutdownNow();
        for (Runnable task : tasks)
            ((ScheduledFuture<?>) task).cancel(true);
        return tasks;
    }
}
