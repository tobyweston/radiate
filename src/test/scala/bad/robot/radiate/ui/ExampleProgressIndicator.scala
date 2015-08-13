package bad.robot.radiate.ui

import java.awt.{Color, Graphics, Graphics2D}
import java.util.concurrent.{Executors, TimeUnit}
import javax.swing.WindowConstants._
import javax.swing.{JComponent, JFrame, JLayer, JPanel}

import bad.robot.radiate.ui.ProgressIndicatorS._
import bad.robot.radiate.Progress
import bad.robot.radiate.activity.Progressing

import bad.robot.radiate.ui.swing.Debug._

object ExampleProgressIndicator extends App {

  val indicator = setupWindow
  updateProgressInAThread(indicator)
  Thread.sleep(5000)
  updateProgressInAThread(indicator)

  private def setupWindow: ProgressIndicatorS = {
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

    val indicator = new ProgressIndicatorS
    frame.add(new JLayer[JComponent](panel, indicator))
    frame.setVisible(true)
    indicator
  }

  private def updateProgressInAThread(indicator: ProgressIndicatorS) {
    val threadPool = Executors.newScheduledThreadPool(1)
    val progress = Array(0)
    threadPool.scheduleAtFixedRate(new Runnable() {
      def run(): Unit = {
        var goneBackwards = false
        progress(0) = progress(0) + 50
        if (progress(0) > 50 && !goneBackwards)
          goneBackwards = true
        if (progress(0) <= 100)
          indicator.setVisibilityBasedOn(Progressing, new Progress(progress(0), maximum))

      }
    }, 1, 1, TimeUnit.SECONDS)
  }
}

object Example extends App {
  val a = Array(0)
  a(0) = 1
  println(a(0))
  a(0) = a(0) + 1
  println(a(0))

}