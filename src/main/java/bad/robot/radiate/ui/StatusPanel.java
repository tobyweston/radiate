package bad.robot.radiate.ui;

import bad.robot.radiate.Activity;
import bad.robot.radiate.NullProgress;
import bad.robot.radiate.Progress;
import bad.robot.radiate.Status;
import bad.robot.radiate.monitor.Information;
import bad.robot.radiate.monitor.Observable;
import bad.robot.radiate.monitor.Observer;
import bad.robot.radiate.ui.swing.Region;
import bad.robot.radiate.ui.swing.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.Callable;

import static bad.robot.radiate.Activity.Busy;
import static bad.robot.radiate.Status.*;
import static bad.robot.radiate.ui.swing.Composite.applyWithComposite;
import static bad.robot.radiate.ui.swing.Composite.getAlphaComposite;
import static bad.robot.radiate.ui.swing.Region.Percentage.EightyPercent;
import static bad.robot.radiate.ui.swing.Region.getReducedRegion;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.abbreviate;

public class StatusPanel extends JPanel implements Observer {

    private static final Color Red = new Color(200, 0, 0);
    private static final Color Green = new Color(0, 200, 0);
    private static final Color Grey = new Color(64, 64, 64);

    private final ProgressIndicator progressIndicator = new ProgressIndicator();
    private final BusyIndicator busyIndicator = new BusyIndicator();
    private final ErrorIndicator errorIndicator = new ErrorIndicator();
    private final int identifier;

    private Status status = Unknown;
    private Activity activity = Busy;
    private Progress progress = new Progress(0, 100);
    private String text;

    public StatusPanel(JFrame parent, int identifier) {
        parent.add(new JLayer<>(new JLayer<>(new JLayer<>(this, errorIndicator), progressIndicator), busyIndicator));
        this.identifier = identifier;
    }

    @Override
    public void update(Observable source, Status status) {
        this.status = status;
        this.text = null;
        setToolTipText(abbreviate(format("%d. %s", this.identifier, source), 20));
        repaint();
    }

    @Override
    public void update(Observable source, Activity activity, Progress progress) {
        this.activity = activity;
        this.progress = progress;
        repaint();
    }

    @Override
    public void update(Observable source, Information information) {
        this.text = abbreviate(format("%s", source), 20);
    }

    @Override
    public void update(Observable source, Exception exception) {
        update(source, Busy, new NullProgress());
    }

    private Color getColorFrom(Status status) {
        if (status == Ok)
            return Green;
        if (status == Broken)
            return Red;
        return Grey;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        fillBackground(graphics);
        updateText(graphics);
        progressIndicator.setVisiblityBasedOn(activity, progress);
        busyIndicator.setVisiblityBasedOn(activity);
        errorIndicator.setVisiblityBasedOn(activity);
    }

    private void fillBackground(Graphics2D graphics) {
        int width = getWidth();
        int height = getHeight();
        Color colour = getColorFrom(status);
        graphics.setPaint(new GradientPaint(0, 0, colour.darker(), 0 + width, 0 + height, colour.brighter()));
        graphics.fill(new Rectangle2D.Double(0, 0, width, height));
    }

    private void updateText(final Graphics2D graphics) {
        if (text != null) {
            applyWithComposite(graphics, getAlphaComposite(graphics, 0.75f), new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    Rectangle region = getReducedRegion(StatusPanel.this.getBounds(), EightyPercent, EightyPercent);
                    Region.centerRegionWithinComponent(region, StatusPanel.this);
                    Text.drawTextCenteredToRegion(region, graphics, text);
                    return null;
                }
            });
        }
    }

}
