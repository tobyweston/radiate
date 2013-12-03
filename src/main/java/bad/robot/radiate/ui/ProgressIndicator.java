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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static bad.robot.radiate.Activity.Progressing;
import static bad.robot.radiate.ui.FrameRate.videoFramesPerSecond;
import static bad.robot.radiate.ui.Swing.Percentage.*;
import static bad.robot.radiate.ui.Swing.*;
import static java.awt.AlphaComposite.SRC_OVER;
import static java.awt.AlphaComposite.getInstance;
import static java.awt.BasicStroke.CAP_BUTT;
import static java.awt.BasicStroke.JOIN_ROUND;
import static java.awt.Color.white;
import static java.awt.Font.PLAIN;
import static java.awt.RenderingHints.*;
import static java.lang.String.format;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

class ProgressIndicator extends LayerUI<JComponent> implements ActionListener {

    public static final float Transparent = 0.20f;
    public static final int maximum = 100;

    private Progress progress = new Progress(0, maximum);
    private Progress animated = new NullProgress();
    private Timer timer = new Timer(videoFramesPerSecond.asFrequencyInMillis(), this);

    @Override
    public void paint(Graphics g, JComponent component) {
        super.paint(g, component);
        if (!timer.isRunning())
            return;
        Graphics2D graphics = (Graphics2D) g.create();
        Rectangle drawArea = getDrawAreaAndCenterWithin(component);
        drawProgressIndicator(drawArea, graphics, component);
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
        applyWithComposite(graphics, getInstance(SRC_OVER, Transparent), new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                graphics.setColor(white);
                graphics.drawArc(region.x, region.y, region.width, region.height, 90, 360);
                return null;
            }
        });
    }

    private void drawProgressRadial(Rectangle region, Graphics2D graphics) {
        graphics.setPaint(white);
        graphics.draw(new Arc2D.Double(region.x, region.y, region.width, region.height, 90, animated.asAngle(), Arc2D.OPEN));
    }

    private void drawPercentage(Rectangle parent, Graphics2D graphics) {
        Rectangle region = getReducedRegion(parent, EightyPercent);
        setFontScaledToRegion(region, graphics, animated.toString(), new Font("Arial", PLAIN, 12));

        FontRenderContext renderContext = graphics.getFontRenderContext();
        GlyphVector vector = graphics.getFont().createGlyphVector(renderContext, animated.toString());
        Rectangle visualBounds = vector.getVisualBounds().getBounds();

        Double x = region.x + (parent.width / 2) - (visualBounds.getWidth() / 2);
        Double y = region.y + (parent.height / 2) + (visualBounds.getHeight() / 2);
        graphics.drawString(animated.toString(), x.floatValue(), y.floatValue());
    }

    private void drawNumberOfBuilds(JComponent component, final Graphics2D graphics) {
        final String numberOfBuilds = format("running %d build%s", progress.numberOfBuilds(), progress.numberOfBuilds() > 1 ? "s" : "");
        Rectangle drawArea = getReducedRegionAsSquare(component, FiftyPercent);
        centerRegionWithinComponent(drawArea, component);
        setFontScaledToRegion(drawArea, graphics, numberOfBuilds, new Font("Arial", PLAIN, 10));
        final Point center = Swing.getCenterPointOfTextWithinRegion(drawArea, graphics, graphics.getFont(), numberOfBuilds);
        Swing.applyWithComposite(graphics, getInstance(SRC_OVER, Transparent), new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                graphics.drawString(numberOfBuilds, center.x, center.y + (center.y / 3)); // nudge down y
                return null;
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (timer.isRunning()) {
            updateProgressReadyToAnimate();
            repaint();
            if (animated.complete())
                stop();
        }
    }

    private void updateProgressReadyToAnimate() {
        if (animated.lessThan(progress))
            animated.increment();
        else if (animated.greaterThan(progress))
            animated.decrement();
    }

    private void repaint() {
        firePropertyChange("tick", 0, 1);
    }

    @Override
    public void applyPropertyChange(PropertyChangeEvent event, JLayer layer) {
        if ("tick".equals(event.getPropertyName()))
            layer.repaint();
    }

    public void setVisiblityBasedOn(Activity activity, Progress progress) {
        if (activity == Progressing) {
            this.progress = progress;
            start();
        } else {
            stop();
        }
    }

    private void start() {
        if (!timer.isRunning())
            animated = new Progress(0, maximum);
        timer.start();
    }

    private void stop() {
        timer.stop();
    }

    public static class Example {

        public static void main(String[] args) throws InterruptedException {
            ProgressIndicator indicator = setupWindow();
            updateProgressInAThread(indicator);
//            Thread.sleep(6000);
//            updateProgressInAThread(indicator);
//            Thread.sleep(12000);
//            updateProgressInAThread(indicator);
        }

        private static ProgressIndicator setupWindow() {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            JPanel panel = new JPanel() {
                @Override
                public void paint(Graphics g) {
                    super.paint(g);
                    Graphics2D graphics = (Graphics2D) g.create();
                    Swing.drawCentreLines(this.getBounds(), graphics);
                    graphics.dispose();
                }
            };
            panel.setBackground(Color.lightGray);
            ProgressIndicator indicator = new ProgressIndicator();
            frame.add(new JLayer<>(panel, indicator));
            frame.setVisible(true);
            return indicator;
        }

        private static void updateProgressInAThread(final ProgressIndicator indicator) {
            ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(1);
            final Integer[] progress = {0};
            threadPool.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    boolean goneBackwards = false;
                    progress[0] = progress[0] + 10;
                    if (progress[0] > 50 && !goneBackwards)
                        goneBackwards = true;
//                    if (goneBackwards)
//                        progress[0] = progress[0] = 16;
                    if (progress[0] <= 100)
                        indicator.setVisiblityBasedOn(Progressing, new Progress(progress[0], maximum));
                }
            }, 1, 1, TimeUnit.SECONDS);
        }
    }
}
