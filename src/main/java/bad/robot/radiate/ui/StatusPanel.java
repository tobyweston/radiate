package bad.robot.radiate.ui;

import bad.robot.radiate.Status;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static bad.robot.radiate.Status.Broken;
import static bad.robot.radiate.Status.Ok;
import static java.awt.Color.BLACK;

public class StatusPanel extends Canvas {

    private static final Color Red    = new Color(200, 0, 0);
    private static final Color Green  = new Color(0, 200, 0);
    private static final Color Amber  = new Color(220, 150, 0);
    private static final Color Grey  = new Color(64, 64, 64);

    private Status status;

    public StatusPanel(Status status) {
        this.status = status;
    }

    public void update(Status status) {
        this.status = status;
        repaint();
    }

    @Override
    public void paint(Graphics graphics) {
        setBackground(BLACK);
        fill(graphics, 0, 0, getWidth(), getHeight());
    }

    private void fill(Graphics graphix, int x, int y, int width, int height) {
        Graphics2D graphics = (Graphics2D) graphix;
        Color colour = getColorFrom(status);
        graphics.setPaint(new GradientPaint(x, y, colour, x + width, y + height, colour.brighter()));
        graphics.fill(new Rectangle2D.Double(x, y, width, height));

        // to give it a border
        graphix.setColor(colour);
        graphix.draw3DRect(x, y, width, height, true);
    }

    private Color getColorFrom(Status status) {
        if (status == Ok)
            return Green;
        if (status == Broken)
            return Red;
        return Grey;
    }
}
