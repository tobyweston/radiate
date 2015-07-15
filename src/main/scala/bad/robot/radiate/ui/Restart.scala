package bad.robot.radiate.ui

import java.awt._
import java.awt.event.{AWTEventListener, KeyAdapter, KeyEvent}

import bad.robot.radiate.MainS._
import bad.robot.radiate.monitor.MonitoringTasksFactoryS

class RestartS(taskFactory: MonitoringTasksFactoryS, keyCode: Int) extends KeyAdapter with AWTEventListener {

  override def keyPressed(event: KeyEvent) {
    if (event.getKeyCode == keyCode) {
      Radiate.stop()
      Radiate.start(taskFactory, Radiate.getCurrentFrames)
    }
  }

  def eventDispatched(event: AWTEvent) {
    if (event.getID == KeyEvent.KEY_PRESSED) keyPressed(event.asInstanceOf[KeyEvent])
  }
}