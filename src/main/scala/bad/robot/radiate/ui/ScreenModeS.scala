package bad.robot.radiate.ui

import java.util.function.Consumer
import javax.swing.JFrame

// todo replace with funciton
trait ScreenModeS extends Consumer[JFrame] {
  def accept(frame: JFrame)
}
