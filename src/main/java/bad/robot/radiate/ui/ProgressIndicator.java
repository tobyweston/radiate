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
import static bad.robot.radiate.ui.FrameRate.videoFramesPerSecond;
import static bad.robot.radiate.ui.Swing.*;
import static java.awt.AlphaComposite.SRC_OVER;
import static java.awt.AlphaComposite.getInstance;
import static java.awt.BasicStroke.CAP_BUTT;
import static java.awt.BasicStroke.JOIN_ROUND;
import static java.awt.Color.white;
import static java.awt.RenderingHints.*;

class ProgressIndicator extends LayerUI<JComponent> implements ActionListener {

    private int progress = 0;
    private int max = 100;
    private boolean running;
    private Timer timer;

    @Override
    public void paint(Graphics g, JComponent component) {
        super.paint(g, component);
        if (!running)
            return;
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
        if (progress <= max) {
            setLineWidth(region, graphics);
            drawBackgroundRadial(region, graphics);
            drawProgressRadial(region, graphics);
            drawPercentage(region, progress, graphics);
        }
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
        int angle = -(int) ((float) progress / max * 360);
        graphics.draw(new Arc2D.Double(region.x, region.y, region.width, region.height, 90, angle, Arc2D.OPEN));
    }

    private void drawPercentage(Rectangle parent, int progress, Graphics2D graphics) {
        String text = progress + "%";
        Font font = new Font("Arial", Font.PLAIN, 12);
        Rectangle region = getReducedRegion(parent, 1.15);
        setFontScaledToRegion(region, graphics, text, font);

        FontRenderContext renderContext = graphics.getFontRenderContext();
        GlyphVector vector = graphics.getFont().createGlyphVector(renderContext, text);
        Rectangle visualBounds = vector.getVisualBounds().getBounds();

        Double x = region.x + (parent.width / 2) - (visualBounds.getWidth() / 2);
        Double y = region.y + (parent.height / 2) + (visualBounds.getHeight() / 2);
        graphics.drawString(text, x.floatValue(), y.floatValue());
    }

    public void start() {
        if (running)
            return;
        running = true;
        timer = new Timer(videoFramesPerSecond.asFrequencyInMillis(), this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (running) {
            progress++;
            firePropertyChange("tick", 0, 1);
            if (progress >= max)
                timer.stop();
        }
    }

    @Override
    public void applyPropertyChange(PropertyChangeEvent event, JLayer layer) {
        if ("tick".equals(event.getPropertyName()))
            layer.repaint();
    }

    public void setVisiblityBasedOn(State state) {
        if (state == Progressing)
            start();
        else
            stop();
    }

    public void stop() {
        if (running) {
            timer.stop();
            running = false;
            // fadeOut
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);
        JPanel panel = new JPanel();
        panel.setBackground(Color.lightGray);
        ProgressIndicator indicator = new ProgressIndicator();
        frame.add(new JLayer<>(panel, indicator));
        frame.setVisible(true);
        indicator.start();
    }

}
