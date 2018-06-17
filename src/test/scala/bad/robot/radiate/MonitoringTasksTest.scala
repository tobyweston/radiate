package bad.robot.radiate

import java.util.concurrent.ScheduledFuture

import bad.robot.radiate.monitor._
import bad.robot.radiate.scalamock._
import bad.robot.radiate.specs2.monitoringTasksAsResult
import org.scalamock.specs2.IsolatedMockFactory
import org.specs2.mutable.Specification

import scalaz.-\/
import scalaz.syntax.either._

class MonitoringTasksTest extends Specification with IsolatedMockFactory {

  val factory = mock[MonitoringTasksFactory]
  val monitor = mock[Monitor]

  "Gathers tasks" >> {
    val task = stub[MonitoringTask]

    (factory.notifyObservers(_: Exception)).expects(*).anyNumberOfTimes
    (factory.create _).expects().once.returning(List(task).right)

    new MonitoringTasks(factory, monitor)
  }

  "When no tasks are generated, notify observer" >> {
    (factory.notifyObservers(_: Exception)).expects(anyTypedOf[NothingToMonitorException]).once
    (factory.create _).expects().once.returning(List().right)

    new MonitoringTasks(factory, monitor)
  }

  "Exceptions thrown when gathering tasks will notify observers" >> {
    val arbitaryError = ParseError("example")

    (factory.notifyObservers(_: Error)).expects(arbitaryError).once
    (factory.notifyObservers(_: Information)).expects(RestartRequired()).once
    (factory.notifyObservers(_: Exception)).expects(anyTypedOf[NothingToMonitorException]).once
    (factory.create _).expects().once.returning(-\/(arbitaryError))

    new MonitoringTasks(factory, monitor)
  }

  "Start and stop" >> {
    val tasks = List(new RandomStatus)
    val scheduled = List.empty[ScheduledFuture[_]]

    (factory.create _).expects().returning(tasks.right).once
    (monitor.start _).expects(tasks).returning(scheduled).once
    (monitor.cancel _).expects(scheduled).once

    val monitoring = new MonitoringTasks(factory, monitor)
    monitoring.start()
    monitoring.stop()

    true must_== true
  }
}
