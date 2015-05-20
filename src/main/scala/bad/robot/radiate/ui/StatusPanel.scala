package bad.robot.radiate.ui

import java.awt._
import java.awt.geom.Rectangle2D
import javax.swing._

import bad.robot.radiate.LegacyJavaConverters.toActivity
import bad.robot.radiate.LegacyJavaConverters.toProgress
import bad.robot.radiate.monitor.{ObservableS, ObserverS}
import bad.robot.radiate.ui.StatusPanelS._
import bad.robot.radiate.ui.Transparency.SeventyFivePercent
import bad.robot.radiate.ui.swing.Composite.transparentComposite
import bad.robot.radiate.ui.swing.CompositeS._
import bad.robot.radiate.ui.swing.Region.Percentage.EightyPercent
import bad.robot.radiate.ui.swing.Region.getReducedRegion
import bad.robot.radiate.ui.swing.{Region, Text}
import bad.robot.radiate.{Broken, Busy, Ok, Unknown, _}
import org.apache.commons.lang3.StringUtils.abbreviate

object StatusPanelS {
  private val Red = new Color(200, 0, 0)
  private val Green = new Color(0, 200, 0)
  private val Grey = new Color(64, 64, 64)
}

class StatusPanelS(parent: JFrame, identifier: Int) extends JPanel with ObserverS {

  private val progressIndicator = new ProgressIndicator
  private val overtimeIndicator = new OvertimeIndicator
  private val busyIndicator = new BusyIndicator
  private val errorIndicator = new ErrorIndicator

  private var status: StatusS = Unknown
  private var activity: ActivityS = Busy
  private var progress = new ProgressS(0, 100)
  private var text = null

  parent.add(new JLayer[JComponent](new JLayer[JComponent](new JLayer[JComponent](new JLayer[JComponent](this, errorIndicator), progressIndicator), overtimeIndicator), busyIndicator))

  override def update(source: ObservableS, status: StatusS) {
    this.status = status
    this.text = null
    setToolTipText(abbreviate(s"$identifier. $source", 20))
    repaint()
  }

  override def update(source: ObservableS, activity: ActivityS, progress: ProgressS) {
    this.activity = activity
    this.progress = progress
    repaint()
  }

  override def update(source: ObservableS, exception: Exception) {
    update(source, Busy, new NullProgressS)
  }

  private def getColorFrom(status: StatusS): Color = {
    status match {
      case Ok => Green
      case Broken => Red
      case _ => Grey
    }
  }

  override protected def paintComponent(g: Graphics) {
    val graphics = g.asInstanceOf[Graphics2D]
    fillBackground(graphics)
    updateText(graphics)
    progressIndicator.setVisibilityBasedOn(activity, progress)
    overtimeIndicator.setVisibilityBasedOn(activity, progress)
    busyIndicator.setVisibilityBasedOn(activity)
    errorIndicator.setVisibilityBasedOn(activity)
  }

  private def fillBackground(graphics: Graphics2D) {
    val width = getWidth
    val height = getHeight
    val colour = getColorFrom(status)
    graphics.setPaint(new GradientPaint(0, 0, colour.darker, width, height, colour.brighter))
    graphics.fill(new Rectangle2D.Double(0, 0, width, height))
  }

  private def updateText(graphics: Graphics2D) {
    if (text != null) {
      applyWithComposite(graphics, transparentComposite(graphics, SeventyFivePercent)) {
        val region = getReducedRegion(StatusPanelS.this.getBounds, EightyPercent, EightyPercent)
        Region.centerRegionWithinComponent(region, StatusPanelS.this)
        Text.drawTextCenteredToRegion(region, graphics, text)
      }
    }
  }
}