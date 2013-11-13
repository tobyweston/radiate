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
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;

import static bad.robot.radiate.State.Progressing;
import static java.awt.AlphaComposite.SRC_OVER;
import static java.awt.AlphaComposite.getInstance;
import static java.awt.BasicStroke.CAP_ROUND;
import static java.awt.BasicStroke.JOIN_ROUND;
import static java.awt.Color.WHITE;
import static java.awt.Color.white;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;

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
        drawProgressIndicator(drawArea, graphics, component);
        graphics.dispose();
    }

    private void drawProgressIndicator(Rectangle region, Graphics2D graphics, JComponent component) {
        if (progress <= max) {
            drawBackground(region, graphics, Color.gray);
            drawRadial(region, graphics);
            fillCenter(region, graphics, (JLayer) component);
            drawPercentage(new Rectangle(region.x, region.y, region.width / 2, region.height / 2), progress, graphics);
        }
    }

    private void drawBackground(Rectangle region, Graphics2D graphics, Color color) {
        graphics.setColor(color);
        graphics.fill(new Ellipse2D.Double(region.x, region.y, region.width, region.height));
    }

    private void drawRadial(Rectangle region, Graphics2D graphics) {
        int reductionPercentage = 20;
        int size = Math.min(region.width, region.height) / reductionPercentage;
        graphics.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
        graphics.setStroke(new BasicStroke(size / 4, CAP_ROUND, JOIN_ROUND));
        graphics.setPaint(white);
        int angle = -(int) ((float) progress / max * 360);
        graphics.fillArc(region.x, region.y, region.width, region.height, 90, angle);
    }

    private void fillCenter(Rectangle region, Graphics2D graphics, JLayer component) {
        int offset = 15;
        graphics.setColor(component.getView().getBackground());
        graphics.fill(new Ellipse2D.Double(region.x + offset, region.y + offset, region.width - (offset * 2), region.height - (offset * 2)));
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
        timer = new Timer(50, this);
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

    private void fadeOut(int width, int height, Graphics2D graphics, float fade) {
        Composite urComposite = graphics.getComposite();
        float alpha = .3f * fade;
        if (alpha >= 0.0f && alpha <= 1.0f)
            graphics.setComposite(getInstance(SRC_OVER, alpha));
        graphics.fillRect(0, 0, width, height);
        graphics.setComposite(urComposite);
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
