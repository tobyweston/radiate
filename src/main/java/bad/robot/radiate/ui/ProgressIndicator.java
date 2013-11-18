package bad.robot.radiate.ui;

import bad.robot.radiate.State;

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

import static bad.robot.radiate.State.Progressing;
import static bad.robot.radiate.ui.Swing.*;
import static java.awt.AlphaComposite.SRC_OVER;
import static java.awt.AlphaComposite.getInstance;
import static java.awt.BasicStroke.CAP_BUTT;
import static java.awt.BasicStroke.JOIN_ROUND;
import static java.awt.Color.white;
import static java.awt.RenderingHints.*;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

class ProgressIndicator extends LayerUI<JComponent> {

    private Progress progress = new Progress(0, 100);
    private Timer timer = new ProgressUpdateTimer(new ProgressUpdateActionListener(this));

    @Override
    public void paint(Graphics g, JComponent component) {
        super.paint(g, component);
//        if (!timer.isRunning())
//            return;
        Graphics2D graphics = (Graphics2D) g.create();
        Rectangle drawArea = getDrawArea(component);
        drawProgressIndicator(drawArea, graphics);
        graphics.dispose();
    }

    private static Rectangle getDrawArea(JComponent component) {
        int size = Math.min(component.getWidth(), component.getHeight());
        Double padding = size * 0.4;
        Double square = size - padding * 2;
        return new Rectangle(padding.intValue(), padding.intValue(), square.intValue(), square.intValue());
    }

    private void drawProgressIndicator(Rectangle region, Graphics2D graphics) {
        setLineWidth(region, graphics);
        drawBackgroundRadial(region, graphics);
        drawProgressRadial(region, graphics);
        drawPercentage(region, progress, graphics);
    }

    private void setLineWidth(Rectangle region, Graphics2D graphics) {
        float size = Math.min(region.width, region.height) * 0.10f;
        graphics.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(KEY_STROKE_CONTROL, VALUE_STROKE_PURE); // stops the wobble
        graphics.setStroke(new BasicStroke(Math.max(1, size), CAP_BUTT, JOIN_ROUND));
    }

    private void drawBackgroundRadial(final Rectangle region, final Graphics2D graphics) {
        applyWithComposite(graphics, getInstance(SRC_OVER, 0.20f), new Callable<Void>() {
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
        graphics.draw(new Arc2D.Double(region.x, region.y, region.width, region.height, 90, progress.asAngle(), Arc2D.OPEN));
    }

    private void drawPercentage(Rectangle parent, Progress progress, Graphics2D graphics) {
        Font font = new Font("Arial", Font.PLAIN, 12);
        Rectangle region = getReducedRegion(parent, 1.15);
        setFontScaledToRegion(region, graphics, progress.toString(), font);

        FontRenderContext renderContext = graphics.getFontRenderContext();
        GlyphVector vector = graphics.getFont().createGlyphVector(renderContext, progress.toString());
        Rectangle visualBounds = vector.getVisualBounds().getBounds();

        Double x = region.x + (parent.width / 2) - (visualBounds.getWidth() / 2);
        Double y = region.y + (parent.height / 2) + (visualBounds.getHeight() / 2);
        graphics.drawString(progress.toString(), x.floatValue(), y.floatValue());
    }

    @Override
    public void applyPropertyChange(PropertyChangeEvent event, JLayer layer) {
        if ("tick".equals(event.getPropertyName())) {
            progress.increment();
            layer.repaint();
            if (progress.complete())
                timer.stop();
        }
    }

    public void setVisiblityBasedOn(State state) {
        if (state == Progressing)
            timer.start();
        else
            timer.stop();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);
        JPanel panel = new JPanel();
        panel.setBackground(Color.lightGray);
        ProgressIndicator indicator = new ProgressIndicator();
        frame.add(new JLayer<>(panel, indicator));
        frame.setVisible(true);
        indicator.setVisiblityBasedOn(Progressing);
    }

    private class ProgressUpdateTimer extends Timer {
        public ProgressUpdateTimer(ActionListener listener) {
            super(FrameRate.videoFramesPerSecond.asFrequencyInMillis(), listener);
        }

        @Override
        public void start() {
            if (isRunning())
                return;
            super.start();
        }

        @Override
        public void stop() {
            if (isRunning()) {
                super.stop();
                // fadeOut, how?
            }
        }
    }

    private class ProgressUpdateActionListener implements ActionListener {

        private final ProgressIndicator component;

        private ProgressUpdateActionListener(ProgressIndicator component) {
            this.component = component;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (timer.isRunning())
                component.firePropertyChange("tick", 0, 1);
        }
    }
}
