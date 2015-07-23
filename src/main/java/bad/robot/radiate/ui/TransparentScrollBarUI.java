package bad.robot.radiate.ui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;

@Deprecated
class TransparentScrollBarUI extends BasicScrollBarUI {
    private final Dimension dimension = new Dimension();

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return new JButton() {
            @Override
            public Dimension getPreferredSize() {
                return dimension;
            }
        };
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return new JButton() {
            @Override
            public Dimension getPreferredSize() {
                return dimension;
            }
        };
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
        // needed to hide the "paint track", aka the scroll bar
    }

    @Override
    protected void paintThumb(Graphics g, JComponent component, Rectangle region) {
        Graphics2D graphics = (Graphics2D) g.create();
        graphics.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
        JScrollBar scrollbar = (JScrollBar) component;
        if (!scrollbar.isEnabled() || region.width > region.height) {
            return;
        } else if (isDragging) {
            graphics.setPaint(new Color(25, 25, 25, 251));
        } else if (isThumbRollover()) {
            graphics.setPaint(new Color(30, 30, 30, 251));
        } else {
            graphics.setPaint(new Color(39, 39, 39, 251));
        }
        graphics.fillRoundRect(region.x, region.y, region.width, region.height, 10, 10);
        graphics.setPaint(Color.darkGray);
        graphics.drawRoundRect(region.x, region.y, region.width, region.height, 10, 10);
        graphics.dispose();
    }

    @Override
    protected void setThumbBounds(int x, int y, int width, int height) {
        super.setThumbBounds(x, y, width, height);
        scrollbar.repaint();
    }
}
