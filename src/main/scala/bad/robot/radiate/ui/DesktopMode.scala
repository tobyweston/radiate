package bad.robot.radiate.ui

import java.awt.Color.darkGray
import java.awt._
import javax.swing._

/** aka resizable screen mode */
class DesktopMode(bounds: Rectangle) extends ScreenMode {

  def accept(frame: JFrame) {
    frame.setSize((bounds.width * 0.80).toInt, (bounds.height * 0.80).toInt)
    frame.setLocation(bounds.x, bounds.y)
    frame.getContentPane.setBackground(darkGray)
  }
}