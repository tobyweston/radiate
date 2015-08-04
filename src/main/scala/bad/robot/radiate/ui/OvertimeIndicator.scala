package bad.robot.radiate.ui

import java.awt.BasicStroke.{CAP_BUTT, JOIN_ROUND}
import java.awt.Color.white
import java.awt.Font.PLAIN
import java.awt.RenderingHints._
import java.awt._
import java.awt.event.{ActionEvent, ActionListener}
import java.awt.geom.Arc2D
import java.beans.PropertyChangeEvent
import javax.swing._
import javax.swing.plaf.LayerUI

import bad.robot.radiate.ui.FrameRate.videoFramesPerSecond
import bad.robot.radiate.ui.Transparency.Transparent
import bad.robot.radiate.ui.swing.Composite.{applyWithComposite, transparentComposite}
import bad.robot.radiate.ui.swing.Percentage.{FiftyPercent, TwentyPercent}
import bad.robot.radiate.ui.swing.Region.{centerRegionWithinComponent, getReducedRegionAsSquare}
import bad.robot.radiate.ui.swing.Text.{getCenterPointOfTextWithinRegion, setFontScaledToRegion}
import bad.robot.radiate.{Activity, Progress, Progressing}

import scala.math._

class OvertimeIndicator extends LayerUI[JComponent] {
  private val timer = new Timer(5, new AnimationActionListenerS)
  private val fadeTimer = new Timer(videoFramesPerSecond.asFrequencyInMillis, new FadeActionListenerS)

  private var fade: Fade = _
  private var overtimeIndicatorPosition = 90
  private var transparency = Transparent

  override def paint(g: Graphics, component: JComponent) {
    super.paint(g, component)
    val graphics = g.create.asInstanceOf[Graphics2D]
    applyWithComposite(graphics, transparentComposite(transparency)) {
      val drawArea = getDrawAreaAndCenterWithin(component)
      drawOvertimeIndicator(drawArea, graphics, component)
    }
    graphics.dispose()
  }

  private def getDrawAreaAndCenterWithin(component: JComponent): Rectangle = {
    val drawArea = getReducedRegionAsSquare(component, TwentyPercent)
    centerRegionWithinComponent(drawArea, component)
    drawArea
  }

  private def drawOvertimeIndicator(region: Rectangle, graphics: Graphics2D, component: JComponent) {
    setLineWidth(region, graphics)
    drawBackgroundRadial(region, graphics)
    drawBusyRadial(region, graphics)
    drawOvertimeText(component, graphics)
  }

  private def setLineWidth(region: Rectangle, graphics: Graphics2D) {
    val size = min(region.width, region.height) * 0.10f
    graphics.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON)
    graphics.setRenderingHint(KEY_STROKE_CONTROL, VALUE_STROKE_PURE)
    graphics.setStroke(new BasicStroke(Math.max(1, size), CAP_BUTT, JOIN_ROUND))
  }

  private def drawBackgroundRadial(region: Rectangle, graphics: Graphics2D) {
    if (timer.isRunning) {
      applyWithComposite(graphics, transparentComposite(graphics, Transparency.TwentyPercent)) {
        graphics.setColor(white)
        graphics.drawArc(region.x, region.y, region.width, region.height, 90, 360)
      }
    }
  }

  private def drawBusyRadial(region: Rectangle, graphics: Graphics2D) {
    graphics.setPaint(white)
    overtimeIndicatorPosition -= 1
    val lengthOfTail = 60
    for (i <- 0 until lengthOfTail) {
      val segment: Int = i
      val transparency = Transparent.increase(i / lengthOfTail.toFloat)
      applyWithComposite(graphics, transparentComposite(graphics, transparency)) {
        graphics.draw(new Arc2D.Double(region.x, region.y, region.width, region.height, overtimeIndicatorPosition - segment, 1, Arc2D.OPEN))
      }
    }
  }

  private def drawOvertimeText(component: JComponent, graphics: Graphics2D) {
    val numberOfBuilds = "build overtime"
    val drawArea = getReducedRegionAsSquare(component, FiftyPercent)
    centerRegionWithinComponent(drawArea, component)
    setFontScaledToRegion(drawArea, graphics, numberOfBuilds, new Font("Arial", PLAIN, 10))
    applyWithComposite(graphics, transparentComposite(graphics, Transparency.TwentyPercent)) {
      val center = getCenterPointOfTextWithinRegion(drawArea, graphics, graphics.getFont(), numberOfBuilds)
      graphics.drawString(numberOfBuilds, center.x, center.y + (center.y / 3)) // nudge down y
    }
  }

  override def applyPropertyChange(event: PropertyChangeEvent, layer: JLayer[_ <: JComponent]): Unit = {
    if ("animate" == event.getPropertyName)
      layer.repaint()
    if ("fade" == event.getPropertyName) {
      transparency = Transparency(event.getNewValue.asInstanceOf[Float])
      layer.repaint()
    }
  }

  def setVisibilityBasedOn(activity: Activity, progress: Progress) = activity match {
    case Progressing if progress.complete => start()
    case _ => stop()
  }

  private def start() {
    if (!timer.isRunning) {
      timer.start()
      fade = new FadeIn
    }
    fadeTimer.start()
  }

  private def stop() {
    if (timer.isRunning)
      fade = new FadeOut
    timer.stop()
  }

  private class AnimationActionListenerS extends ActionListener {
    def actionPerformed(e: ActionEvent) {
      firePropertyChange("animate", 0, 1)
    }
  }

  private class FadeActionListenerS extends ActionListener {
    def actionPerformed(e: ActionEvent) {
      fade.fireEvent(getPropertyChangeListeners.toList)
    }
  }

}