package bad.robot.radiate.monitor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class MonitoringThreadFactory implements ThreadFactory {

    private final AtomicInteger threadCount = new AtomicInteger();

    @Override
    public Thread newThread(Runnable runnable) {
        return new Thread(runnable, "monitoring-thread-" + threadCount.getAndIncrement());
    }
}
