package bad.robot.radiate.ui;

import bad.robot.radiate.Activity;
import bad.robot.radiate.NullProgress;
import bad.robot.radiate.Progress;

import javax.swing.*;
import javax.swing.plaf.LayerUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Arc2D;
import java.beans.PropertyChangeEvent;
import java.util.concurrent.Callable;

import static bad.robot.radiate.Activity.Progressing;
import static bad.robot.radiate.ui.FrameRate.videoFramesPerSecond;
import static bad.robot.radiate.ui.swing.Composite.applyWithComposite;
import static bad.robot.radiate.ui.swing.Composite.getAlphaComposite;
import static bad.robot.radiate.ui.swing.Region.Percentage.*;
import static bad.robot.radiate.ui.swing.Region.centerRegionWithinComponent;
import static bad.robot.radiate.ui.swing.Region.getReducedRegion;
import static bad.robot.radiate.ui.swing.Region.getReducedRegionAsSquare;
import static bad.robot.radiate.ui.swing.Text.*;
import static java.awt.AlphaComposite.SRC_OVER;
import static java.awt.AlphaComposite.getInstance;
import static java.awt.BasicStroke.CAP_BUTT;
import static java.awt.BasicStroke.JOIN_ROUND;
import static java.awt.Color.white;
import static java.awt.Font.PLAIN;
import static java.awt.RenderingHints.*;
import static java.lang.String.format;

class ProgressIndicator extends LayerUI<JComponent> implements ActionListener {

    public static final float Transparency = 0.20f;
    public static final int maximum = 100;

    private Progress progress = new Progress(0, maximum);
    private Progress animation = new NullProgress();
    private Timer timer = new Timer(videoFramesPerSecond.asFrequencyInMillis(), this);
    private Timer fadeTimer = new Timer(videoFramesPerSecond.asFrequencyInMillis(), this);
    private Fade fade = new FadeIn();
    private float alpha = 0.0f; // transparent

    @Override
    public void paint(Graphics g, final JComponent component) {
        super.paint(g, component);
        final Graphics2D graphics = (Graphics2D) g.create();
        applyWithComposite(graphics, getInstance(SRC_OVER, alpha), new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                Rectangle drawArea = getDrawAreaAndCenterWithin(component);
                drawProgressIndicator(drawArea, graphics, component);
                return null;
            }
        });
        graphics.dispose();
    }

    private Rectangle getDrawAreaAndCenterWithin(JComponent component) {
        Rectangle drawArea = getReducedRegionAsSquare(component, TwentyPercent);
        centerRegionWithinComponent(drawArea, component);
        return drawArea;
    }

    private void drawProgressIndicator(Rectangle region, Graphics2D graphics, JComponent component) {
        setLineWidth(region, graphics);
        drawBackgroundRadial(region, graphics);
        drawProgressRadial(region, graphics);
        drawPercentage(region, graphics);
        if (progress.numberOfBuilds() > 1)
            drawNumberOfBuilds(component, graphics);
    }

    private void setLineWidth(Rectangle region, Graphics2D graphics) {
        float size = Math.min(region.width, region.height) * 0.10f;
        graphics.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(KEY_STROKE_CONTROL, VALUE_STROKE_PURE); // stops the wobble
        graphics.setStroke(new BasicStroke(Math.max(1, size), CAP_BUTT, JOIN_ROUND));
    }

    private void drawBackgroundRadial(final Rectangle region, final Graphics2D graphics) {
        if (timer.isRunning()) {
            applyWithComposite(graphics, getAlphaComposite(graphics, Transparency), new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    graphics.setColor(white);
                    graphics.drawArc(region.x, region.y, region.width, region.height, 90, 360);
                    return null;
                }
            });
        }
    }

    private void drawProgressRadial(Rectangle region, Graphics2D graphics) {
        graphics.setPaint(white);
        graphics.draw(new Arc2D.Double(region.x, region.y, region.width, region.height, 90, animation.asAngle(), Arc2D.OPEN));
    }

    private void drawPercentage(Rectangle parent, Graphics2D graphics) {
        Rectangle region = getReducedRegion(parent, EightyPercent);
        setFontScaledToRegion(region, graphics, animation.toString(), new Font("Arial", PLAIN, 12));

        FontRenderContext renderContext = graphics.getFontRenderContext();
        GlyphVector vector = graphics.getFont().createGlyphVector(renderContext, animation.toString());
        Rectangle visualBounds = vector.getVisualBounds().getBounds();

        Double x = region.x + (parent.width / 2) - (visualBounds.getWidth() / 2);
        Double y = region.y + (parent.height / 2) + (visualBounds.getHeight() / 2);
        graphics.drawString(animation.toString(), x.floatValue(), y.floatValue());
    }

    private void drawNumberOfBuilds(JComponent component, final Graphics2D graphics) {
        final String numberOfBuilds = format("running %d build%s", progress.numberOfBuilds(), progress.numberOfBuilds() > 1 ? "s" : "");
        final Rectangle drawArea = getReducedRegionAsSquare(component, FiftyPercent);
        centerRegionWithinComponent(drawArea, component);
        applyWithComposite(graphics, getAlphaComposite(graphics, Transparency), new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                setFontScaledToRegion(drawArea, graphics, numberOfBuilds, new Font("Arial", PLAIN, 10));
                Point center = getCenterPointOfTextWithinRegion(drawArea, graphics, graphics.getFont(), numberOfBuilds);
                graphics.drawString(numberOfBuilds, center.x, center.y + (center.y / 3)); // nudge down y
                return null;
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        fade.fireEvent(getPropertyChangeListeners());
        if (timer.isRunning()) {
            updateProgressReadyToAnimate();
            firePropertyChange("animateRadial", 0, 1);
            if (animation.complete()) {
                stop();
            }
        }
    }

    private void updateProgressReadyToAnimate() {
        if (animation.lessThan(progress))
            animation.increment();
        else if (animation.greaterThan(progress))
            animation.decrement();
    }

    @Override
    public void applyPropertyChange(PropertyChangeEvent event, JLayer layer) {
        if ("animateRadial".equals(event.getPropertyName()))
            layer.repaint();
        if ("fade".equals(event.getPropertyName())) {
            alpha = (float) event.getNewValue();
            layer.repaint();
        }
    }

    public void setVisiblityBasedOn(Activity activity, Progress progress) {
        if (activity == Progressing && !progress.complete()) {
            this.progress = progress;
            start();
        } else {
            stop();
        }
    }

    private void start() {
        if (!timer.isRunning()) {
            animation = new Progress(0, maximum);
            fade = new FadeIn();
        }
        timer.start();
        fadeTimer.start();
    }

    private void stop() {
        if (timer.isRunning())
            fade = new FadeOut();
        timer.stop();
    }

}
