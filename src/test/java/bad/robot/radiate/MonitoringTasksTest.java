package bad.robot.radiate;

import bad.robot.radiate.monitor.*;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import static bad.robot.radiate.RestartRequired.restartRequired;
import static java.util.Collections.emptyList;

public class MonitoringTasksTest {

    @Rule public final JUnitRuleMockery context = new JUnitRuleMockery();

    private final MonitoringTasksFactory factory = context.mock(MonitoringTasksFactory.class);
    private final Monitor monitor = context.mock(Monitor.class);
    
    @Test
    public void gathersTasks() {
        context.checking(new Expectations() {{
            ignoring(factory).notifyObservers(with(any(Exception.class)));
            oneOf(factory).create();
        }});
        new MonitoringTasks(factory, monitor);
    }

    @Test
    public void whenNoTasksAreGeneratedNotifyObserver() {
        context.checking(new Expectations() {{
            oneOf(factory).notifyObservers(with(any(NothingToMonitorException.class)));
            oneOf(factory).create(); will(returnValue(emptyList()));
        }});
        new MonitoringTasks(factory, monitor);
    }

    @Test
    public void exceptionsGatheringTasksNotifyObservers() {
        final Exception exception = new RuntimeException();
        context.checking(new Expectations() {{
            oneOf(factory).create(); will(throwException(exception));
            oneOf(factory).notifyObservers(exception);
            oneOf(factory).notifyObservers(restartRequired());
            oneOf(factory).notifyObservers(with(any(NothingToMonitorException.class)));
        }});
        new MonitoringTasks(factory, monitor);
    }

    @Test
    public void startAndStop() {
        final List<MonitoringTask> tasks = Arrays.<MonitoringTask>asList(new RandomStatus());
        final Iterable<ScheduledFuture<?>> scheduled = emptyList();
        context.checking(new Expectations() {{
            oneOf(factory).create(); will(returnValue(tasks));
            oneOf(monitor).start(tasks); will(returnValue(scheduled));
            oneOf(monitor).stop(scheduled);
        }});

        MonitoringTasks monitoring = new MonitoringTasks(factory, monitor);
        monitoring.start();
        monitoring.stop();
    }

}
