package bad.robot.radiate

import bad.robot.radiate.monitor.MonitoringTasksFactory._
import bad.robot.radiate.ui.FrameFactory._

object Main extends App {
  lazy val Radiate = new Application
  Radiate.start(singleAggregate, fullScreen)
}
