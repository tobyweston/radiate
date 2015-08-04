package bad.robot.radiate.ui

import java.util.function.Consumer
import javax.swing.JFrame

// todo replace with function
trait ScreenMode extends Consumer[JFrame] {
  def accept(frame: JFrame)
}
