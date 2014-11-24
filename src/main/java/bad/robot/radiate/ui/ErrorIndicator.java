package bad.robot.radiate.ui;

import bad.robot.radiate.Activity;

import javax.swing.*;
import javax.swing.plaf.LayerUI;
import java.awt.*;
import java.awt.geom.Ellipse2D;

import static bad.robot.radiate.Activity.Error;
import static java.awt.BasicStroke.CAP_ROUND;
import static java.awt.BasicStroke.JOIN_ROUND;
import static java.awt.Color.white;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;

class ErrorIndicator extends LayerUI<JComponent> {

    private Boolean redraw = false;

    @Override
    public void paint(Graphics g, JComponent component) {
        super.paint(g, component);
        int width = component.getWidth();
        int height = component.getHeight();
        if (redraw) {
            Graphics2D graphics = (Graphics2D) g.create();
            drawErrorIndicator(width, height, graphics);
            graphics.dispose();
        }
    }

    private void drawErrorIndicator(int width, int height, Graphics2D graphics) {
        int reductionPercentage = 20;
        int size = Math.min(width, height) / reductionPercentage;
        int x = width / 2;
        int y = height / 2;
        graphics.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
        graphics.setStroke(new BasicStroke(size / 4, CAP_ROUND, JOIN_ROUND));
        graphics.setPaint(white);
        graphics.draw(new Ellipse2D.Double(100, 100, 200, 200));
    }

    public void setVisibilityBasedOn(Activity activity) {
        if (activity == Error)
            redraw = true;
    }

}
