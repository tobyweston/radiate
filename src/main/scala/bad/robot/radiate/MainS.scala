package bad.robot.radiate

import bad.robot.radiate.monitor.MonitoringTasksFactoryS._
import bad.robot.radiate.ui.FrameFactoryS._

object MainS extends App {
  lazy val Radiate = new ApplicationS
  Radiate.start(singleAggregate, fullScreen)
}
