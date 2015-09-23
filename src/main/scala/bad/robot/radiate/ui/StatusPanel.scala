package bad.robot.radiate.ui

import java.awt._
import java.awt.geom.Rectangle2D
import javax.swing._

import bad.robot.radiate.monitor.{Information, Observable, Observer}
import bad.robot.radiate.ui.StatusPanel._
import bad.robot.radiate.ui.Transparency.SeventyFivePercent
import bad.robot.radiate.ui.swing.Composite.{transparentComposite, _}
import bad.robot.radiate.ui.swing.Percentage.EightyPercent
import bad.robot.radiate.ui.swing.Region.{getReducedRegion, _}
import bad.robot.radiate.ui.swing.Text._
import bad.robot.radiate._
import activity._
import org.apache.commons.lang3.StringUtils.abbreviate

object StatusPanel {
  private val Red = new Color(200, 0, 0)
  private val Green = new Color(0, 200, 0)
  private val Grey = new Color(64, 64, 64)
}

class StatusPanel(parent: JFrame, identifier: Int) extends JPanel with Observer {

  private val progressIndicator = new ProgressIndicatorS
  private val overtimeIndicator = new OvertimeIndicator
  private val busyIndicator = new BusyIndicatorS
  private val errorIndicator = new ErrorIndicator

  private var status: Status = Unknown
  private var activity: Activity = Busy
  private var progress = new Progress(0, 100)
  private var text: String = _

  parent.add(new JLayer[JComponent](new JLayer[JComponent](new JLayer[JComponent](new JLayer[JComponent](this, errorIndicator), progressIndicator), overtimeIndicator), busyIndicator))

  override def update(source: Observable, status: Status) {
    this.status = status
    this.text = null
    setToolTipText(abbreviate(s"$identifier. $source", 20))
    repaint()
  }

  override def update(source: Observable, activity: Activity, progress: Progress) {
    this.activity = activity
    this.progress = progress
    repaint()
  }

  override def update(source: Observable, information: Information): Unit = () /** ignore information updates **/

  override def update(source: Observable, error: Error) {
    update(source, Busy, new NullProgress)
  }

  override def update(source: Observable, exception: Exception) {
    update(source, Busy, new NullProgress)
  }

  private def getColorFrom(status: Status): Color = {
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
        val region = getReducedRegion(StatusPanel.this.getBounds, EightyPercent, EightyPercent)
        centerRegionWithinComponent(region, StatusPanel.this)
        drawTextCenteredToRegion(region, graphics, text)
      }
    }
  }
}