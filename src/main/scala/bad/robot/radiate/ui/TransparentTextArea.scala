package bad.robot.radiate.ui

import java.awt.Font.BOLD
import java.awt._
import javax.swing._

class TransparentTextArea extends JTextArea {
  setOpaque(false)
  setFont(UIManager.getFont("Button.font").deriveFont(BOLD, 11.0f))
  setForeground(Color.white)
  setBackground(Color.black)
  setLineWrap(true)
  setWrapStyleWord(true)
  setEditable(false)
}