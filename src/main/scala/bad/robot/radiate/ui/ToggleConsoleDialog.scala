package bad.robot.radiate.ui

import java.awt._
import java.awt.event.{AWTEventListener, KeyAdapter, KeyEvent}
import java.awt.event.KeyEvent.{VK_F1, VK_I}

class ToggleConsoleDialog(dialog: TransparentDialog) extends KeyAdapter with AWTEventListener {

  override def keyPressed(event: KeyEvent) {
    if (event.getKeyCode == VK_F1 || event.getKeyCode == VK_I) dialog.setVisible(!dialog.isVisible)
  }

  def eventDispatched(event: AWTEvent) {
    if (event.getID == KeyEvent.KEY_PRESSED) keyPressed(event.asInstanceOf[KeyEvent])
  }
}