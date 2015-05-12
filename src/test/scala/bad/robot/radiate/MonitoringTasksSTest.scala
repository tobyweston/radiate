package bad.robot.radiate

import java.util.concurrent.ScheduledFuture

import bad.robot.radiate.RestartRequiredS._
import bad.robot.radiate.monitor._
import org.scalamock.specs2.MockContext
import org.specs2.mutable.Specification

class MonitoringTasksSTest extends Specification {

  "Gathers tasks" in new MockContext {
    val factory = mock[MonitoringTasksFactoryS]
    val monitor = mock[MonitorS]
    val task = stub[MonitoringTaskS]

    (factory.notifyObservers(_: Exception)).expects(*).anyNumberOfTimes
    (factory.create _).expects().once.returning(List(task))

    new MonitoringTasksS(factory, monitor)
  }

  "When no tasks are generated, notify observer" in new MockContext {
    val factory = mock[MonitoringTasksFactoryS]
    val monitor = mock[MonitorS]

    (factory.notifyObservers(_: Exception)).expects(anyTypedOf[Exception, NothingToMonitorExceptionS]).once
    (factory.create _).expects().once.returning(List())

    new MonitoringTasksS(factory, monitor)
  }

  "Exceptions thrown when gathering tasks will notify observers" in new MockContext {
    val factory = mock[MonitoringTasksFactoryS]
    val monitor = mock[MonitorS]

    val exception = new Exception
    
    (factory.create _).expects().throws(exception).once
    (factory.notifyObservers(_: Exception)).expects(exception).once
    (factory.notifyObservers(_: InformationS)).expects(restartRequired).once
    // for finally block
    (factory.notifyObservers(_: Exception)).expects(anyTypedOf[Exception, NothingToMonitorExceptionS]).once

    new MonitoringTasksS(factory, monitor)
  }

  "Start and stop" in new MockContext {
    val tasks = List(new RandomStatusS)
    val scheduled = List.empty[ScheduledFuture[_]]

    val factory = mock[MonitoringTasksFactoryS]
    val monitor = mock[MonitorS]

    (factory.create _).expects().returning(tasks).once
    (monitor.start _).expects(tasks).returning(scheduled).once
    (monitor.cancel _).expects(scheduled).once

    val monitoring = new MonitoringTasksS(factory, monitor)
    monitoring.start()
    monitoring.stop()
  }
}
