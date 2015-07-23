package bad.robot.radiate.ui

import java.awt._
import java.awt.event.KeyEvent.VK_C
import java.awt.event.{KeyAdapter, KeyEvent, KeyListener}
import java.lang.String.format
import javax.swing.WindowConstants.DISPOSE_ON_CLOSE
import javax.swing._

object ConsoleS {

  private def calculateSize(owner: Frame): Dimension = {
    val parent = owner.getSize
    val dimension = new Dimension(parent)
    dimension.setSize(shrink(parent.getWidth), shrink(parent.getHeight))
    dimension
  }

  private def shrink(dimension: Double): Double = {
    dimension - (dimension / 10)
  }
}

class ConsoleS(owner: Frame) extends TransparentDialogS("", owner) {

  private val text: JTextArea = new TransparentTextArea

  makeResizeable()
  val dialog = getJDialog
  dialog.setSize(ConsoleS.calculateSize(owner))
  dialog.setLocationRelativeTo(owner)
  dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE)
  dialog.add(new TransparentJScrollPaneS(text))
  text.addKeyListener { (event: KeyEvent) => if (event.getKeyCode == VK_C) clear() }

  def append(string: String) {
    text.append(format("%s\n", string))
  }

  private def clear() {
    text.setText(null)
  }

  implicit def toKeyListener(f: KeyEvent => Unit): KeyListener = new KeyAdapter {
    override def keyReleased(e: KeyEvent): Unit = f.apply(e)
  }

}