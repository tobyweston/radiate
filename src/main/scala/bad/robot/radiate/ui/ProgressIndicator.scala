package bad.robot.radiate.ui

import java.awt.BasicStroke.{CAP_BUTT, JOIN_ROUND}
import java.awt.Color.white
import java.awt.Font.PLAIN
import java.awt.RenderingHints._
import java.awt._
import java.awt.event.{ActionEvent, ActionListener}
import java.awt.font.{FontRenderContext, GlyphVector}
import java.awt.geom.Arc2D
import java.beans.PropertyChangeEvent
import javax.swing._
import javax.swing.plaf.LayerUI

import bad.robot.radiate._
import bad.robot.radiate.ui.FrameRateS.videoFramesPerSecond
import bad.robot.radiate.ui.ProgressIndicatorS._
import bad.robot.radiate.ui.swing.CompositeS.{applyWithComposite, transparentComposite}
import bad.robot.radiate.ui.swing.PercentageS._
import bad.robot.radiate.ui.swing.RegionS._
import bad.robot.radiate.ui.swing.TextS.{getCenterPointOfTextWithinRegion, setFontScaledToRegion}

import scala.math._

object ProgressIndicatorS {
  val maximum: Int = 100
}

class ProgressIndicatorS extends LayerUI[JComponent] with ActionListener {
  private var progress = ProgressS(0, maximum)
  private var animation: ProgressS = new NullProgressS
  private val timer = new Timer(videoFramesPerSecond.asFrequencyInMillis, this)
  private val fadeTimer = new Timer(videoFramesPerSecond.asFrequencyInMillis, this)
  private var fade: FadeScala = new FadeInScala
  private var transparency = Transparency.Transparent

  override def paint(g: Graphics, component: JComponent) {
    super.paint(g, component)
    val graphics = g.create.asInstanceOf[Graphics2D]
    applyWithComposite(graphics, transparentComposite(transparency)) {
      val drawArea = getDrawAreaAndCenterWithin(component)
      drawProgressIndicator(drawArea, graphics, component)
    }
    graphics.dispose()
  }

  private def getDrawAreaAndCenterWithin(component: JComponent): Rectangle = {
    val drawArea = getReducedRegionAsSquare(component, TwentyPercent)
    centerRegionWithinComponent(drawArea, component)
    drawArea
  }

  private def drawProgressIndicator(region: Rectangle, graphics: Graphics2D, component: JComponent) {
    setLineWidth(region, graphics)
    drawBackgroundRadial(region, graphics)
    drawProgressRadial(region, graphics)
    drawPercentage(region, graphics)
    if (progress.numberOfBuilds > 1)
      drawNumberOfBuilds(component, graphics)
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

  private def drawProgressRadial(region: Rectangle, graphics: Graphics2D) {
    graphics.setPaint(white)
    graphics.draw(new Arc2D.Double(region.x, region.y, region.width, region.height, 90, animation.asAngle, Arc2D.OPEN))
  }

  private def drawPercentage(parent: Rectangle, graphics: Graphics2D) {
    val region = getReducedRegion(parent, EightyPercent)
    setFontScaledToRegion(region, graphics, animation.toString, new Font("Arial", PLAIN, 12))
    val renderContext: FontRenderContext = graphics.getFontRenderContext
    val vector: GlyphVector = graphics.getFont.createGlyphVector(renderContext, animation.toString)
    val visualBounds: Rectangle = vector.getVisualBounds.getBounds
    val x = region.x + (parent.width / 2) - (visualBounds.getWidth / 2)
    val y = region.y + (parent.height / 2) + (visualBounds.getHeight / 2)
    graphics.drawString(animation.toString, x.floatValue, y.floatValue)
  }

  private def drawNumberOfBuilds(component: JComponent, graphics: Graphics2D) {
    val numberOfBuilds = s"running ${progress.numberOfBuilds} build${if (progress.numberOfBuilds > 1) "s" else ""}"
    val drawArea = getReducedRegionAsSquare(component, FiftyPercent)
    centerRegionWithinComponent(drawArea, component)
    applyWithComposite(graphics, transparentComposite(graphics, Transparency.TwentyPercent)) {
      setFontScaledToRegion(drawArea, graphics, numberOfBuilds, new Font("Arial", PLAIN, 10))
      val center = getCenterPointOfTextWithinRegion(drawArea, graphics, graphics.getFont, numberOfBuilds)
      graphics.drawString(numberOfBuilds, center.x, center.y + (center.y / 3)); // nudge down y
    }
  }

  def actionPerformed(event: ActionEvent) {
    fade.fireEvent(getPropertyChangeListeners.toList)
    if (timer.isRunning) {
      updateProgressReadyToAnimate()
      firePropertyChange("animateRadial", 0, 1)
      if (animation.complete) {
        stop()
      }
    }
  }

  private def updateProgressReadyToAnimate() {
    if (animation.lessThan(progress))
      animation.increment()
    else if (animation.greaterThan(progress))
      animation.decrement()
  }

  override def applyPropertyChange(event: PropertyChangeEvent, layer: JLayer[_ <: JComponent]): Unit = {
    if ("animateRadial" == event.getPropertyName)
      layer.repaint()
    if ("fade" == event.getPropertyName) {
      transparency = new Transparency(event.getNewValue.asInstanceOf[Float])
      layer.repaint()
    }
  }

  def setVisibilityBasedOn(activity: ActivityS, progress: ProgressS) = activity match {
    case Progressing if !progress.complete => this.progress = progress; start()
    case _ => stop()
  }

  private def start() {
    if (!timer.isRunning) {
      animation = ProgressS(0, maximum)
      fade = new FadeInScala
    }
    timer.start()
    fadeTimer.start()
  }

  private def stop() {
    if (timer.isRunning)
      fade = new FadeOutS
    timer.stop()
  }
}