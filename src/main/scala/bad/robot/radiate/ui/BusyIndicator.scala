package bad.robot.radiate.ui

import java.awt.AlphaComposite.{SRC_OVER, getInstance}
import java.awt.BasicStroke.{CAP_ROUND, JOIN_ROUND}
import java.awt.Color.white
import java.awt.RenderingHints.{KEY_ANTIALIASING, VALUE_ANTIALIAS_ON}
import java.awt._
import java.awt.event.{ActionEvent, ActionListener}
import java.beans.PropertyChangeEvent
import javax.swing._
import javax.swing.plaf.LayerUI

import bad.robot.radiate.{ActivityS, Busy}
import bad.robot.radiate.ui.FrameRate.videoFramesPerSecond
import math._

class BusyIndicatorS extends LayerUI[JComponent] with ActionListener {
  private var running = false
  private var fadingOut = false
  private var timer: Timer = _
  private var angle = 0
  private var fadeCount = 0

  private val fadeLimit = 15

  override def paint(g: Graphics, component: JComponent) {
    val width = component.getWidth
    val height = component.getHeight
    super.paint(g, component)
    if (running) {
      val graphics = g.create.asInstanceOf[Graphics2D]
      val fade = fadeCount.toFloat / fadeLimit.toFloat
      fadeOut(width, height, graphics, fade)
      drawBusyIndicator(width, height, graphics, fade)
      graphics.dispose()
    }
  }

  private def drawBusyIndicator(width: Int, height: Int, graphics: Graphics2D, fade: Float) {
    val reductionPercentage: Int = 20
    val size = min(width, height) / reductionPercentage
    val x = width / 2
    val y = height / 2
    graphics.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON)
    graphics.setStroke(new BasicStroke(size / 4, CAP_ROUND, JOIN_ROUND))
    graphics.setPaint(white)
    graphics.rotate(Pi * angle / 180, x, y)
    for (i <- 0 until 12) {
      val scale = (11.0f - i.toFloat) / 11.0f
      graphics.drawLine(x + size, y, x + size * 2, y)
      graphics.rotate(-Pi / 6, x, y)
      setCompositeToTransparent(graphics, scale * fade)
    }
  }

  private def fadeOut(width: Int, height: Int, graphics: Graphics2D, fade: Float) {
    val original = graphics.getComposite
    val alpha =.3f * fade
    setCompositeToTransparent(graphics, alpha)
    graphics.fillRect(0, 0, width, height)
    graphics.setComposite(original)
  }

  private def setCompositeToTransparent(graphics: Graphics2D, alpha: Float) {
    if (alpha >= 0.0f && alpha <= 1.0f) graphics.setComposite(getInstance(SRC_OVER, alpha))
  }

  private def start() {
    if (!running) {
      running = true
      fadingOut = false
      fadeCount = 0
      timer = new Timer(videoFramesPerSecond.asFrequencyInMillis, this)
      timer.start
    }
  }

  override def actionPerformed(e: ActionEvent) {
    if (running) {
      firePropertyChange("tick", 0, 1)
      angle += 3
      if (angle >= 360)
        angle = 0
      if (fadingOut) {
        fadeCount -= 1
        if (fadeCount <= 0) {
          running = false
          fadingOut = false
          timer.stop()
        }
      } else if (fadeCount < fadeLimit) {
        fadeCount += 1
      }
    }
  }

  override def applyPropertyChange(event: PropertyChangeEvent, layer: JLayer[_ <: JComponent]): Unit = {
    if ("tick" == event.getPropertyName) layer.repaint()
  }

  def setVisibilityBasedOn(activity: ActivityS) = activity match {
    case Busy => start
    case _ => stop
  }

  private def stop() = {
    fadingOut = true
  }
}