package bad.robot.radiate.ui

import java.awt._
import javax.swing._

import bad.robot.radiate.ui.TransparentScrollPaneLayoutS._

object TransparentScrollPaneLayoutS {
  private def calculateAvailableRegion(parent: Container, scrollPane: JScrollPane): Rectangle = {
    val available = scrollPane.getBounds
    available.x = 0
    available.y = 0
    
    val insets = parent.getInsets
    available.x = insets.left
    available.y = insets.top
    available.width = available.width - (insets.left + insets.right)
    available.height = available.height - (insets.top + insets.bottom)
    available
  }

  private def calculateVerticalScrollBarRegion(availableRegion: Rectangle): Rectangle = {
    val region = new Rectangle
    region.width = 12
    region.height = availableRegion.height
    region.x = availableRegion.x + availableRegion.width - region.width
    region.y = availableRegion.y
    region
  }
}

class TransparentScrollPaneLayoutS extends ScrollPaneLayout {
  override def layoutContainer(parent: Container) {
    val scrollPane = parent.asInstanceOf[JScrollPane]
    val availableRegion = calculateAvailableRegion(parent, scrollPane)
    
    if (viewport != null) 
      viewport.setBounds(availableRegion)
    
    if (vsb != null) {
      vsb.setVisible(true)
      vsb.setBounds(calculateVerticalScrollBarRegion(availableRegion))
    }
  }
}