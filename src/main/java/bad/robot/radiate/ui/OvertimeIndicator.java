package bad.robot.radiate.ui;

import bad.robot.radiate.Activity;
import bad.robot.radiate.Progress;

import javax.swing.*;
import javax.swing.plaf.LayerUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;
import java.beans.PropertyChangeEvent;

import static bad.robot.radiate.Activity.Progressing;
import static bad.robot.radiate.ui.FrameRate.videoFramesPerSecond;
import static bad.robot.radiate.ui.Transparency.Transparent;
import static bad.robot.radiate.ui.swing.Composite.applyWithComposite;
import static bad.robot.radiate.ui.swing.Composite.transparentComposite;
import static bad.robot.radiate.ui.swing.Region.Percentage.FiftyPercent;
import static bad.robot.radiate.ui.swing.Region.Percentage.TwentyPercent;
import static bad.robot.radiate.ui.swing.Region.centerRegionWithinComponent;
import static bad.robot.radiate.ui.swing.Region.getReducedRegionAsSquare;
import static bad.robot.radiate.ui.swing.Text.getCenterPointOfTextWithinRegion;
import static bad.robot.radiate.ui.swing.Text.setFontScaledToRegion;
import static java.awt.BasicStroke.CAP_BUTT;
import static java.awt.BasicStroke.JOIN_ROUND;
import static java.awt.Color.white;
import static java.awt.Font.PLAIN;
import static java.awt.RenderingHints.*;
import static java.lang.String.format;

@Deprecated
class OvertimeIndicator extends LayerUI<JComponent> {

    private Timer timer = new Timer(5, new AnimationActionListener());
    private Timer fadeTimer = new Timer(videoFramesPerSecond.asFrequencyInMillis(), new FadeActionListener());
    private Fade fade; // = new FadeIn();
    private int overtimeIndicatorPosition = 90;
    private Transparency transparency = Transparent;

    @Override
    public void paint(Graphics g, final JComponent component) {
        super.paint(g, component);
        final Graphics2D graphics = (Graphics2D) g.create();
        applyWithComposite(graphics, transparentComposite(transparency), () -> {
            Rectangle drawArea = getDrawAreaAndCenterWithin(component);
            drawOvertimeIndicator(drawArea, graphics, component);
            return null;
        });
        graphics.dispose();
    }

    private Rectangle getDrawAreaAndCenterWithin(JComponent component) {
        Rectangle drawArea = getReducedRegionAsSquare(component, TwentyPercent);
        centerRegionWithinComponent(drawArea, component);
        return drawArea;
    }

    private void drawOvertimeIndicator(Rectangle region, Graphics2D graphics, JComponent component) {
        setLineWidth(region, graphics);
        drawBackgroundRadial(region, graphics);
        drawBusyRadial(region, graphics);
        drawOvertimeText(component, graphics);
    }

    private void setLineWidth(Rectangle region, Graphics2D graphics) {
        float size = Math.min(region.width, region.height) * 0.10f;
        graphics.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(KEY_STROKE_CONTROL, VALUE_STROKE_PURE); // stops the wobble
        graphics.setStroke(new BasicStroke(Math.max(1, size), CAP_BUTT, JOIN_ROUND));
    }

    private void drawBackgroundRadial(final Rectangle region, final Graphics2D graphics) {
        if (timer.isRunning()) {
            applyWithComposite(graphics, transparentComposite(graphics, Transparency.TwentyPercent), () -> {
                graphics.setColor(white);
                graphics.drawArc(region.x, region.y, region.width, region.height, 90, 360);
                return null;
            });
        }
    }

    private void drawBusyRadial(final Rectangle region, final Graphics2D graphics) {
        graphics.setPaint(white);
        overtimeIndicatorPosition--;
        int lengthOfTail = 60;
        for (int i = 0; i < lengthOfTail; i++) {
            final int segment = i;
            Transparency transparency = Transparent.increase(i / (float) lengthOfTail);
            applyWithComposite(graphics, transparentComposite(graphics, transparency), () -> {
                graphics.draw(new Arc2D.Double(region.x, region.y, region.width, region.height, overtimeIndicatorPosition - segment, 1, Arc2D.OPEN));
                return null;
            });
        }
    }

    private void drawOvertimeText(JComponent component, final Graphics2D graphics) {
        final String numberOfBuilds = "build overtime";
        final Rectangle drawArea = getReducedRegionAsSquare(component, FiftyPercent);
        centerRegionWithinComponent(drawArea, component);
        setFontScaledToRegion(drawArea, graphics, numberOfBuilds, new Font("Arial", PLAIN, 10));
        applyWithComposite(graphics, transparentComposite(graphics, Transparency.TwentyPercent), () -> {
            Point center = getCenterPointOfTextWithinRegion(drawArea, graphics, graphics.getFont(), numberOfBuilds);
            graphics.drawString(numberOfBuilds, center.x, center.y + (center.y / 3)); // nudge down y
            return null;
        });
    }

    @Override
    public void applyPropertyChange(PropertyChangeEvent event, JLayer layer) {
        if ("animate".equals(event.getPropertyName()))
            layer.repaint();
        if ("fade".equals(event.getPropertyName())) {
            transparency = new Transparency((float) event.getNewValue());
            layer.repaint();
        }
    }

    public void setVisibilityBasedOn(Activity activity, Progress progress) {
        if (activity == Progressing && progress.complete()) {
            start();
        } else {
            stop();
        }
    }

    private void start() {
        if (!timer.isRunning()) {
            timer.start();
            fade = new FadeIn();
        }
        fadeTimer.start();
    }

    private void stop() {
        if (timer.isRunning())
            fade = new FadeOut();
        timer.stop();
    }

    private class AnimationActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            firePropertyChange("animate", 0, 1);
        }
    }

    private class FadeActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            fade.fireEvent(getPropertyChangeListeners());
        }
    }
}
