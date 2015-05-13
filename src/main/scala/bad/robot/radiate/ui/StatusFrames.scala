package bad.robot.radiate.ui

import bad.robot.radiate.monitor.ObserverS

class StatusFramesS(factory: FrameFactoryS) {

  private val frames = factory.create

  private[ui] def primary: StatusFrameS = {
     frames.head
  }

  def display() {
    frames.foreach(frame => frame.display())
  }

  def dispose() {
    frames.foreach(frame => frame.dispose())
  }
  
  def createStatusPanels: List[ObserverS] = {
     frames.map(frame => frame.createStatusPanel)
  }
}