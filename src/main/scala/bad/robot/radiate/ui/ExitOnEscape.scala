package bad.robot.radiate.ui

import java.awt._
import java.awt.event.{AWTEventListener, KeyAdapter, KeyEvent}
import java.awt.event.KeyEvent.VK_ESCAPE

class ExitOnEscapeS extends KeyAdapter with AWTEventListener {
  override def keyPressed(e: KeyEvent) {
    if (e.getKeyCode == VK_ESCAPE) System.exit(0)
  }

  def eventDispatched(event: AWTEvent) {
    keyPressed(event.asInstanceOf[KeyEvent])
  }
}