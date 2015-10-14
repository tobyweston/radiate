package bad.robot.radiate.ui

import java.awt.Color.darkGray
import java.awt._
import javax.swing._

class FullScreen(bounds: Rectangle) extends ScreenMode {
  def accept(frame: JFrame) {
    frame.setSize(bounds.width, bounds.height)
    frame.setLocation(bounds.x, bounds.y)
    frame.getContentPane.setBackground(darkGray)
    frame.setUndecorated(true)
  }
}