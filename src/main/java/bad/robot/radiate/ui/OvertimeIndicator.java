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
import java.util.concurrent.Callable;

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

class OvertimeIndicator extends LayerUI<JComponent> implements ActionListener {

    public static final float Transparency = 0.20f;

    private Timer timer = new Timer(5, this);
    private Timer fadeTimer = new Timer(videoFramesPerSecond.asFrequencyInMillis(), this);
    private Fade fade = new FadeIn();
    private int progress = 90;
    private float alpha = 0.0f; // transparent

    @Override
    public void paint(Graphics g, final JComponent component) {
        super.paint(g, component);
        final Graphics2D graphics = (Graphics2D) g.create();
        applyWithComposite(graphics, getInstance(SRC_OVER, alpha), new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                Rectangle drawArea = getDrawAreaAndCenterWithin(component);
                drawOvertimeIndicator(drawArea, graphics, component);
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

    private void drawBusyRadial(final Rectangle region, final Graphics2D graphics) {
        graphics.setPaint(white);
        progress--;
        int lengthOfTail = 60;
        for (int i = 0; i < lengthOfTail; i++) {
            final int bar = i;
            float transparency = 0.0f + (i / ((float) lengthOfTail));
            Swing.applyWithComposite(graphics, getAlphaComposite(graphics, transparency), new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    graphics.draw(new Arc2D.Double(region.x, region.y, region.width, region.height, progress - bar, 1, Arc2D.OPEN));
                    return null;
                }
            });
        }
    }

    private void drawOvertimeText(JComponent component, final Graphics2D graphics) {
        final String numberOfBuilds = format("build overtime");
        final Rectangle drawArea = getReducedRegionAsSquare(component, FiftyPercent);
        centerRegionWithinComponent(drawArea, component);
        setFontScaledToRegion(drawArea, graphics, numberOfBuilds, new Font("Arial", PLAIN, 10));
        Swing.applyWithComposite(graphics, getAlphaComposite(graphics, Transparency), new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                Point center = Swing.getCenterPointOfTextWithinRegion(drawArea, graphics, graphics.getFont(), numberOfBuilds);
                graphics.drawString(numberOfBuilds, center.x, center.y + (center.y / 3)); // nudge down y
                return null;
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (timer.isRunning()) {
            fade.fireEvent(getPropertyChangeListeners());
            firePropertyChange("animate", 0, 1);
        }
    }

    @Override
    public void applyPropertyChange(PropertyChangeEvent event, JLayer layer) {
        if ("animate".equals(event.getPropertyName()))
            layer.repaint();
        if ("fade".equals(event.getPropertyName())) {
            alpha = (float) event.getNewValue();
            layer.repaint();
        }
    }

    public void setVisiblityBasedOn(Activity activity, Progress progress) {
        if (activity == Progressing) {
            start();
        } else {
            stop();
        }
    }

    private void start() {
        timer.start();
        fadeTimer.start();
    }

    private void stop() {
        if (timer.isRunning())
            fade = new FadeOut();
        timer.stop();
    }

    public static class ExampleOvertimeIndicator {

        public static void main(String[] args) throws InterruptedException {
            OvertimeIndicator indicator = setupWindow();
            indicator.setVisiblityBasedOn(Progressing, null);
        }

        private static OvertimeIndicator setupWindow() {
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
            OvertimeIndicator indicator = new OvertimeIndicator();
            frame.add(new JLayer<>(panel, indicator));
            frame.setVisible(true);
            return indicator;
        }

    }

    private static AlphaComposite getAlphaComposite(Graphics2D graphics, float transparency) {
        AlphaComposite current = (AlphaComposite) graphics.getComposite();
        return getInstance(SRC_OVER, current.getAlpha() * transparency);
    }

}
