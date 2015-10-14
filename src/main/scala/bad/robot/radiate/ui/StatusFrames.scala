package bad.robot.radiate.ui

import bad.robot.radiate.monitor.Observer

class StatusFrames(factory: FrameFactory) {

  private val frames = factory.create

  private[ui] def primary: StatusFrame = {
     frames.head
  }

  def display() {
    frames.foreach(frame => frame.display())
  }

  def dispose() {
    frames.foreach(frame => frame.dispose())
  }
  
  def createStatusPanels: List[Observer] = {
     frames.map(frame => frame.createStatusPanel)
  }
}