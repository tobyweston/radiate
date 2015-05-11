package bad.robot.radiate

import bad.robot.radiate.monitor.{MonitoringTaskS, MonitorS, MonitoringTasksFactoryS}
import org.scalamock.specs2.MockContext
import org.specs2.mutable.Specification

class MonitoringTasksSTest extends Specification {

  "gathers tasks" in new MockContext {
    val factory = mock[MonitoringTasksFactoryS]
    val monitor = mock[MonitorS]
    val task = stub[MonitoringTaskS]

    (factory.notifyObservers(_: Exception)).expects(*).anyNumberOfTimes
    (factory.create _).expects().once.returning(List(task))

    new MonitoringTasksS(factory, monitor)
  }

}
