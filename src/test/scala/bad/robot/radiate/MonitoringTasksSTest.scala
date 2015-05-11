package bad.robot.radiate

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

    // todo make a specific subtype NothingToMonitorExceptionS
    (factory.notifyObservers(_: Exception)).expects(*).once
    (factory.create _).expects().once.returning(List())

    new MonitoringTasksS(factory, monitor)
  }
}
