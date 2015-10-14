package bad.robot.radiate.ui

import java.awt.{Color, Graphics, Graphics2D}
import javax.swing.WindowConstants._
import javax.swing.{JComponent, JFrame, JLayer, JPanel}

import bad.robot.radiate.ui.swing.Debug._
import bad.robot.radiate.Progress
import bad.robot.radiate.activity.Progressing

object ExampleOvertimeIndicator extends App {

  val indicator = setupWindow
  indicator.setVisibilityBasedOn(Progressing, new Progress(100, 100))

  private def setupWindow: OvertimeIndicator = {
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

    val indicator = new OvertimeIndicator
    frame.add(new JLayer[JComponent](panel, indicator))
    frame.setVisible(true)
    indicator
  }
}
