package bad.robot.radiate.monitor;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static com.google.code.tempusfugit.concurrency.CallableAdapter.runnable;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Monitor {

    private final ScheduledExecutorService executor;

    public Monitor(ScheduledExecutorService executor) {
        this.executor = executor;
    }

    public List<? extends MonitoringTask> beginMonitoring(List<MonitoringTask> tasks) {
        for (MonitoringTask task : tasks)
            schedule(task);
        return tasks;
    }

    public List<Runnable> shutdown() {
        List<Runnable> tasks = executor.shutdownNow();
        for (Runnable task : tasks)
            ((ScheduledFuture<?>) task).cancel(true);
        return tasks;
    }

    private ScheduledFuture<?> schedule(MonitoringTask task) {
        return executor.scheduleWithFixedDelay(runnable(task), 0, 15, SECONDS);
    }

}
