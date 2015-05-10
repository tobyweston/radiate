package bad.robot.radiate.monitor

import java.util.Arrays.asList
import java.util.concurrent.{RunnableScheduledFuture, ScheduledExecutorService, ScheduledFuture}

import org.scalamock.specs2.MockContext
import org.specs2.mutable.Specification

class MonitorSTest extends Specification {

  "Can stop previously scheduled tasks" in new MockContext {
    val executor = mock[ScheduledExecutorService]
    val task = mock[MonitoringTaskS]
    val future = mock[ScheduledFuture[Any]]

    (executor.scheduleWithFixedDelay _).expects(*, *, * , *).returning(future)
    (future.cancel _).expects(true).once

    val monitor = new ScheduledMonitorS(executor)

    val scheduled = monitor.start(List(task))
    scheduled must have size 1
    monitor.cancel(scheduled)
  }

  "Can terminate monitoring" in new MockContext {
    val executor = mock[ScheduledExecutorService]
    val task = mock[MonitoringTask]
    val future = mock[ScheduledFuture[Any]]
    val waiting = mock[RunnableScheduledFuture[Any]]

    val monitor = new ScheduledMonitorS(executor)

    (executor.shutdownNow _).expects().returning(asList(waiting)).once
    (waiting.cancel _).expects(true).once

    monitor.stop
  }

}