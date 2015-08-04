package bad.robot.radiate.ui

import java.awt.Graphics
import javax.swing._
import javax.swing.plaf.LayerUI

object CompositeLayerUI {
  def apply[C <: JComponent](layers: List[LayerUI[C]]): LayerUI[C] = {
    new CompositeLayerUI[C](layers)
  }
}

class CompositeLayerUI[C <: JComponent](layers: List[LayerUI[C]]) extends LayerUI[C] {

  override def installUI(component: JComponent) {
    layers.foreach(layer => layer.installUI(component))
    super.installUI(component)
  }

  override def paint(graphics: Graphics, component: JComponent) {
    super.paint(graphics, component)
    layers.foreach(layer => layer.paint(graphics, component))
  }

  override def uninstallUI(component: JComponent) {
    layers.foreach(layer => layer.uninstallUI(component))
    super.uninstallUI(component)
  }
}