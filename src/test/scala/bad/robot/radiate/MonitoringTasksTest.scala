package bad.robot.radiate

import java.util.concurrent.ScheduledFuture

import bad.robot.radiate.specs2.monitoringTasksAsResult
import bad.robot.radiate.scalamock._
import bad.robot.radiate.RestartRequired._
import bad.robot.radiate.monitor._
import org.scalamock.specs2.{IsolatedMockFactory}
import org.specs2.mutable.Specification

class MonitoringTasksTest extends Specification with IsolatedMockFactory {

  val factory = mock[MonitoringTasksFactory]
  val monitor = mock[Monitor]

  "Gathers tasks" >> {
    val task = stub[MonitoringTask]

    (factory.notifyObservers(_: Exception)).expects(*).anyNumberOfTimes
    (factory.create _).expects().once.returning(List(task))

    new MonitoringTasksS(factory, monitor)
  }

  "When no tasks are generated, notify observer" >> {
    (factory.notifyObservers(_: Exception)).expects(anyTypedOf[NothingToMonitorException]).once
    (factory.create _).expects().once.returning(List())

    new MonitoringTasksS(factory, monitor)
  }

  "Exceptions thrown when gathering tasks will notify observers" >> {
    val exception = new Exception
    
    (factory.create _).expects().throws(exception).once
    (factory.notifyObservers(_: Exception)).expects(exception).once
    (factory.notifyObservers(_: Information)).expects(restartRequired).once
    // for finally block
    (factory.notifyObservers(_: Exception)).expects(anyTypedOf[NothingToMonitorException]).once

    new MonitoringTasksS(factory, monitor)
  }

  "Start and stop" >> {
    val tasks = List(new RandomStatus)
    val scheduled = List.empty[ScheduledFuture[_]]

    (factory.create _).expects().returning(tasks).once
    (monitor.start _).expects(tasks).returning(scheduled).once
    (monitor.cancel _).expects(scheduled).once

    val monitoring = new MonitoringTasksS(factory, monitor)
    monitoring.start()
    monitoring.stop()

    true must_== true
  }
}
