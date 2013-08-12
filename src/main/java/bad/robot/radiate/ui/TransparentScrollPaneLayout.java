package bad.robot.radiate.ui;

import javax.swing.*;
import java.awt.*;

class TransparentScrollPaneLayout extends ScrollPaneLayout {
    @Override
    public void layoutContainer(Container parent) {
        JScrollPane scrollPane = (JScrollPane) parent;
        Rectangle availableRegion = calculateAvailableRegion(parent, scrollPane);

        if (viewport != null)
            viewport.setBounds(availableRegion);

        if (vsb != null) {
            vsb.setVisible(true);
            vsb.setBounds(calculateVerticalScrollBarRegion(availableRegion));
        }
    }

    private static Rectangle calculateAvailableRegion(Container parent, JScrollPane scrollPane) {
        Rectangle available = scrollPane.getBounds();
        available.x = 0;
        available.y = 0;

        Insets insets = parent.getInsets();
        available.x = insets.left;
        available.y = insets.top;
        available.width = available.width - (insets.left + insets.right);
        available.height = available.height - (insets.top + insets.bottom);
        return available;
    }

    private static Rectangle calculateVerticalScrollBarRegion(Rectangle availableRegion) {
        Rectangle region = new Rectangle();
        region.width = 12;
        region.height = availableRegion.height;
        region.x = availableRegion.x + availableRegion.width - region.width;
        region.y = availableRegion.y;
        return region;
    }
}
