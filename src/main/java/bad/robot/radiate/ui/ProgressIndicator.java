package bad.robot.radiate.ui;

import bad.robot.radiate.State;

import javax.swing.*;
import javax.swing.plaf.LayerUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;

import static bad.robot.radiate.State.Progressing;
import static bad.robot.radiate.ui.FrameRate.videoFramesPerSecond;
import static java.awt.BasicStroke.CAP_BUTT;
import static java.awt.BasicStroke.JOIN_ROUND;
import static java.awt.Color.WHITE;
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
        Rectangle drawArea = new Rectangle(0, 0, component.getWidth(), component.getHeight());
        drawProgressIndicator(drawArea, graphics);
        graphics.dispose();
    }

    private void drawProgressIndicator(Rectangle region, Graphics2D graphics) {
        if (progress <= max) {
            setLineWidth(region, graphics);
            drawBackgroundRadial(region, graphics);
            drawProgressRadial(region, graphics);
            drawPercentage(new Rectangle(region.x, region.y, region.width / 2, region.height / 2), progress, graphics);
        }
    }

    private void setLineWidth(Rectangle region, Graphics2D graphics) {
        float size = Math.min(region.width, region.height) * 0.10f;
        graphics.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(KEY_STROKE_CONTROL, VALUE_STROKE_PURE); // stops the wobble
        graphics.setStroke(new BasicStroke(size, CAP_BUTT, JOIN_ROUND));
    }

    private void drawBackgroundRadial(Rectangle region, Graphics2D graphics) {
        graphics.setColor(Color.gray);
        graphics.drawArc(region.x, region.y, region.width, region.height, 90, 360);
    }

    private void drawProgressRadial(Rectangle region, Graphics2D graphics) {
        graphics.setPaint(white);
        int angle = -(int) ((float) progress / max * 360);
        graphics.draw(new Arc2D.Double(region.x, region.y, region.width, region.height, 90, angle, Arc2D.OPEN));
    }

    private void drawPercentage(Rectangle region, int progress, Graphics2D graphics) {
        String text = progress + "%";
        Font font = new Font("Arial", Font.BOLD, 12);
        FontRenderContext context = graphics.getFontRenderContext();
        graphics.setFont(font);
        Rectangle2D bounds = graphics.getFont().getStringBounds(text, context);

        LineMetrics line = font.getLineMetrics(text, context);
        float xScale = (float) (region.width / bounds.getWidth());
        float yScale = (region.height / (line.getAscent() + line.getDescent()));

        double x = region.x;
        double y = region.y + region.height - (yScale * line.getDescent());
        AffineTransform transformation = AffineTransform.getTranslateInstance(x, y);

        if (xScale > yScale)
            transformation.scale(yScale, yScale);
        else
            transformation.scale(xScale, xScale);

        graphics.setFont(font.deriveFont(transformation));
        graphics.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
        graphics.setColor(WHITE);

        int centerX = region.width / 2;
        int centerY = region.height / 2;
        graphics.drawString(text, centerX, centerY - (int) bounds.getHeight());
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
        frame.setSize(200, 200);
        JPanel panel = new JPanel();
        panel.setBackground(Color.lightGray);
        ProgressIndicator indicator = new ProgressIndicator();
        frame.add(new JLayer<>(panel, indicator));
        frame.setVisible(true);
        indicator.start();
    }
}
