package bad.robot.radiate.ui

import javax.swing.JFrame._
import javax.swing._

import bad.robot.radiate.monitor.ObserverS

import scala.collection.JavaConversions._
import scala.collection._

class StatusFrameScala(index: Int, screen: ScreenMode) extends JFrame {

  private val panels = mutable.MutableList[StatusPanelS]()

  setLayout(new ChessboardLayout(panels))
  setupWindowing(index, screen)

  private def setupWindowing(index: Int, screen: ScreenMode) {
    setDefaultCloseOperation(EXIT_ON_CLOSE)
    setTitle(s"Radiate:$index")
    screen.accept(this)
  }

  def createStatusPanel: ObserverS = {
    val panel = new StatusPanelS(this, panels.size + 1)
    panels += panel
    panel
  }

  def display() {
    if (panels.isEmpty)
      createStatusPanel
    setVisible(true)
  }
}