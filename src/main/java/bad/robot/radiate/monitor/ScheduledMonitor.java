package bad.robot.radiate.monitor;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

public class ScheduledMonitor implements Monitor {

    private final ScheduledExecutorService executor;
    private final int frequency = 30;

    public ScheduledMonitor(ScheduledExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public Iterable<ScheduledFuture<?>> start(Iterable<MonitoringTask> tasks) {
        return stream(tasks.spliterator(), false).map(task -> executor.scheduleWithFixedDelay(task, 0, frequency, SECONDS)).collect(toList());
    }

    @Override
    public void cancel(Iterable<ScheduledFuture<?>> tasks) {
        tasks.forEach(task -> task.cancel(true));
    }

    @Override
    public List<Runnable> stop() {
        List<Runnable> tasks = executor.shutdownNow();
        tasks.stream().forEach(task -> ((ScheduledFuture<?>) task).cancel(true));
        return tasks;
    }
}
