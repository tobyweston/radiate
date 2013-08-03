package bad.robot.radiate.ui;

import bad.robot.radiate.Status;
import bad.robot.radiate.teamcity.SanitisedException;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

import static bad.robot.radiate.Status.*;
import static bad.robot.radiate.ui.UiText.createTextRegion;
import static bad.robot.radiate.ui.UiText.drawText;

public class StatusPanel extends JPanel {

    private static final Color Red = new Color(200, 0, 0);
    private static final Color Green = new Color(0, 200, 0);
    private static final Color Grey = new Color(64, 64, 64);

    private final BusyIndicator busyIndicator = new BusyIndicator();

    private Status status = Busy;
    private String text;

    public StatusPanel(JFrame parent) {
        parent.add(new JLayer<>(this, busyIndicator));
    }

    public void update(Status status) {
        this.status = status;
        this.text = null;
        repaint();
    }

    public void update(Exception exception) {
        this.text = new SanitisedException(exception).getMessage();
        repaint();
    }

    private void addBorder(int x, int y, int width, int height, Graphics2D graphics, Color colour) {
        graphics.setColor(colour);
        graphics.draw3DRect(x + 1, y - 1, width, height, true);
    }

    private Color getColorFrom(Status status) {
        if (status == Ok)
            return Green;
        if (status == Broken)
            return Red;
        return Grey;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        fillBackground((Graphics2D) graphics);
        updateText(graphics);
        updateBusyIndicator();
    }

    private void fillBackground(Graphics2D graphics) {
        int width = getWidth();
        int height = getHeight();
        Color colour = getColorFrom(status);
        graphics.setPaint(new GradientPaint(0, 0, colour.darker(), 0 + width, 0 + height, colour.brighter()));
        graphics.fill(new Rectangle2D.Double(0, 0, width, height));
        addBorder(0, 0, width, height, graphics, colour);
    }

    private void updateText(Graphics graphics) {
        if (text != null) {
            Rectangle region = createTextRegion(0, 0, getWidth(), getHeight());
            drawText(graphics, region, text);
        }
    }

    private void updateBusyIndicator() {
        if (status == Busy)
            busyIndicator.start();
        else
            busyIndicator.stop();
    }
}
