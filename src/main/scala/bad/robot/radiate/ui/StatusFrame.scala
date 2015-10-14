package bad.robot.radiate.ui

import javax.swing.JFrame._
import javax.swing._

import bad.robot.radiate.monitor.Observer

import scala.collection._

class StatusFrame(index: Int, screen: ScreenMode) extends JFrame {

  private val panels = mutable.MutableList[StatusPanel]()

  setLayout(new ChessboardLayout(panels))
  setupWindowing(index, screen)

  private def setupWindowing(index: Int, screen: ScreenMode) {
    setDefaultCloseOperation(EXIT_ON_CLOSE)
    setTitle(s"Radiate:$index")
    screen.accept(this)
  }

  def createStatusPanel: Observer = {
    val panel = new StatusPanel(this, panels.size + 1)
    panels += panel
    panel
  }

  def display() {
    if (panels.isEmpty)
      createStatusPanel
    setVisible(true)
  }
}