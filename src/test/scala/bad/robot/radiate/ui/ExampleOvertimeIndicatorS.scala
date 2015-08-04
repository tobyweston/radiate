package bad.robot.radiate.ui

import java.awt.{Color, Graphics, Graphics2D}
import javax.swing.WindowConstants._
import javax.swing.{JComponent, JFrame, JLayer, JPanel}

import bad.robot.radiate.ui.swing.DebugS._
import bad.robot.radiate.{ProgressS, Progressing}

object ExampleOvertimeIndicatorS extends App {

  val indicator = setupWindow
  indicator.setVisibilityBasedOn(Progressing, new ProgressS(100, 100))

  private def setupWindow: OvertimeIndicatorS = {
    val frame = new JFrame
    frame.setDefaultCloseOperation(EXIT_ON_CLOSE)
    frame.setSize(400, 400)
    val panel = new JPanel() {
      override def paint(g: Graphics) {
        super.paint(g)
        val graphics = g.create.asInstanceOf[Graphics2D]
        drawCentreLines(this.getBounds, graphics)
        graphics.dispose()
      }
    }
    panel.setBackground(Color.lightGray)

    val indicator = new OvertimeIndicatorS
    frame.add(new JLayer[JComponent](panel, indicator))
    frame.setVisible(true)
    indicator
  }
}
