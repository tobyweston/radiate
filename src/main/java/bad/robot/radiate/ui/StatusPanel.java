package bad.robot.radiate.ui;

import bad.robot.radiate.State;
import bad.robot.radiate.Status;
import bad.robot.radiate.monitor.Information;
import bad.robot.radiate.monitor.Observable;
import bad.robot.radiate.monitor.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

import static bad.robot.radiate.State.Busy;
import static bad.robot.radiate.Status.*;
import static bad.robot.radiate.ui.UiText.createTextRegion;
import static bad.robot.radiate.ui.UiText.drawText;
import static java.lang.String.format;

public class StatusPanel extends JPanel implements Observer {

    private static final Color Red = new Color(200, 0, 0);
    private static final Color Green = new Color(0, 200, 0);
    private static final Color Grey = new Color(64, 64, 64);

    private final BusyIndicator busyIndicator = new BusyIndicator();
    private final ErrorIndicator errorIndicator = new ErrorIndicator();
    private final int identifier;

    private Status status = Unknown;
    private State state = Busy;
    private String text;

    public StatusPanel(JFrame parent, int identifier) {
        parent.add(new JLayer<>(this, busyIndicator));
        this.identifier = identifier;
    }

    @Override
    public void update(Observable source, Status status) {
        this.status = status;
        this.text = null;
        setToolTipText(format("%d. %s", identifier, source));
        repaint();
    }

    @Override
    public void update(Observable source, State state) {
        this.state = state;
        repaint();
    }

    @Override
    public void update(Observable source, Information information) {
    }

    @Override
    public void update(Observable source, Exception exception) {
        update(source, Busy);
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
        busyIndicator.setVisiblityBasedOn(state);
    }

    private void fillBackground(Graphics2D graphics) {
        int width = getWidth();
        int height = getHeight();
        Color colour = getColorFrom(status);
        graphics.setPaint(new GradientPaint(0, 0, colour.darker(), 0 + width, 0 + height, colour.brighter()));
        graphics.fill(new Rectangle2D.Double(0, 0, width, height));
    }

    private void updateText(Graphics graphics) {
        if (text != null) {
            Rectangle region = createTextRegion(0, 0, getWidth(), getHeight());
            drawText(graphics, region, text);
        }
    }
    
}
