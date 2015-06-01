package bad.robot.radiate.ui

import bad.robot.radiate.monitor.ObserverS

class StatusFramesScala(factory: FrameFactoryS) {

  private val frames = factory.create

  private[ui] def primary: StatusFrameScala = {
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