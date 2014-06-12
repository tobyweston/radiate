package bad.robot.radiate.monitor;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static com.googlecode.totallylazy.matchers.IterableMatcher.hasSize;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JMock.class)
public class MonitorTest {

    public final Mockery context = new JUnit4Mockery();

    private final ScheduledExecutorService executor = context.mock(ScheduledExecutorService.class);
    private final MonitoringTask task = context.mock(MonitoringTask.class);
    private final ScheduledFuture future = context.mock(ScheduledFuture.class);
    private final RunnableScheduledFuture waiting = context.mock(RunnableScheduledFuture.class);

    private final Monitor monitor = new ScheduledMonitor(executor);

    @Test
    public void canStopPreviouslyScheduledTasks() {
        context.checking(new Expectations() {{
            allowing(executor).scheduleWithFixedDelay(with(any(Runnable.class)), with(any(Long.class)), with(any(Long.class)), with(any(TimeUnit.class))); will(returnValue(future));
            oneOf(future).cancel(true);
        }});
        Iterable<ScheduledFuture<?>> scheduled = monitor.start(asList(task));
        assertThat(scheduled, hasSize(1));
        monitor.cancel(scheduled);
    }

    @Test
    public void canTerminateMonitoring() {
        context.checking(new Expectations() {{
            oneOf(executor).shutdownNow(); will(returnValue(asList(waiting)));
            oneOf(waiting).cancel(true);
        }});
        monitor.stop();
    }
}
