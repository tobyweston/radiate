package bad.robot.radiate.monitor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class MonitoringThreadFactory implements ThreadFactory {

    private final AtomicInteger threadCount = new AtomicInteger();

    @Override
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable, "monitoring-thread-" + threadCount.getAndIncrement()) {
            @Override
            public void interrupt() {
                super.interrupt();
            }
        };
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread t, Throwable e) {
                // do something smart
            }
        });
        return thread;
    }
}
