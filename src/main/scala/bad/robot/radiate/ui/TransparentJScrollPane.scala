package bad.robot.radiate.ui

import javax.swing.ScrollPaneConstants._
import javax.swing._

class TransparentJScrollPaneS(text: JTextArea) extends JScrollPane(text, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_NEVER) {
  setComponentZOrder(getVerticalScrollBar, 0)
  setComponentZOrder(getViewport, 1)
  setLayout(new TransparentScrollPaneLayout)
  setOpaque(false)
  getViewport.setOpaque(false)
  // setBackground(Color.black);
  getVerticalScrollBar.setOpaque(false)
  getVerticalScrollBar.setUI(new TransparentScrollBarUIS)
}