package bad.robot.radiate

import bad.robot.radiate.monitor.{MonitorS, MonitoringTasksFactoryS}
import org.scalamock.specs2.MockContext
import org.specs2.mutable.Specification

class MonitoringTasksSTest extends Specification {

  "gathers tasks" in new MockContext {
    val factory = mock[MonitoringTasksFactoryS]
    val monitor = mock[MonitorS]

    (factory.notifyObservers(_: Exception)).expects(*)
    (factory.create _).expects().once

    new MonitoringTasksS(factory, monitor)
  }

}
