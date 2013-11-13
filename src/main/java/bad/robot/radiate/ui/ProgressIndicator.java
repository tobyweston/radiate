package bad.robot.radiate.ui;

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

import static java.awt.BasicStroke.CAP_ROUND;
import static java.awt.BasicStroke.JOIN_ROUND;
import static java.awt.Color.GRAY;
import static java.awt.Color.WHITE;
import static java.awt.Color.white;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;

class ProgressIndicator extends LayerUI<JComponent> implements ActionListener {

    private final Timer timer;
    private int progress = 0;
    private int max = 100;

    ProgressIndicator() {
        timer = new Timer(50, this);
        timer.start();
    }

    @Override
    public void paint(Graphics g, JComponent component) {
        super.paint(g, component);
        Graphics2D graphics = (Graphics2D) g.create();
        drawProgressIndicator(component.getWidth(), component.getHeight(), graphics, component);
        graphics.dispose();
    }

    private void drawProgressIndicator(int width, int height, Graphics2D graphics, JComponent component) {
        if (progress <= max) {
            drawBackground(width, height, graphics, Color.gray);
            drawRadial(width, height, graphics);
            fillCenter(width, height, graphics, (JLayer) component);
            drawPercentage(progress, graphics, width / 2, height / 2);
//            drawCenterLine(width, height, graphics);
        }
    }

    private void drawCenterLine(int width, int height, Graphics2D graphics) {
        graphics.drawLine(0, 0, width, height);
        graphics.drawLine(width, 0, 0, height);
    }

    private void drawBackground(int width, int height, Graphics2D graphics, Color color) {
        graphics.setColor(color);
        graphics.fill(new Ellipse2D.Double(0, 0, width, height));
    }

    private void drawRadial(int width, int height, Graphics2D graphics) {
        int reductionPercentage = 20;
        int size = Math.min(width, height) / reductionPercentage;
        graphics.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
        graphics.setStroke(new BasicStroke(size / 4, CAP_ROUND, JOIN_ROUND));
        graphics.setPaint(white);
        int angle = -(int) ((float) progress / max * 360);
        graphics.fillArc(0, 0, width, height, 90, angle);
    }

    private void fillCenter(int width, int height, Graphics2D graphics, JLayer component) {
        int offset = 15;
        graphics.setColor(component.getView().getBackground());
        graphics.fill(new Ellipse2D.Double(0 + offset, 0 + offset, width - (offset * 2), height - (offset * 2)));
    }

    private void drawPercentage(int progress, Graphics2D graphics, int width, int height) {
        String text = progress + "%";
        Font font = new Font("Arial", Font.BOLD, 12);
        FontRenderContext context = graphics.getFontRenderContext();
        graphics.setFont(font);
        Rectangle2D bounds = graphics.getFont().getStringBounds(text, context);

        LineMetrics line = font.getLineMetrics(text, context);
        float xScale = (float) (width / bounds.getWidth());
        float yScale = (height / (line.getAscent() + line.getDescent()));

        double x = 0;
        double y = 0 + height - (yScale * line.getDescent());
        AffineTransform transformation = AffineTransform.getTranslateInstance(x, y);

        if (xScale > yScale)
            transformation.scale(yScale, yScale);
        else
            transformation.scale(xScale, xScale);

        graphics.setFont(font.deriveFont(transformation));
        graphics.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
        graphics.setColor(WHITE);

        int centerX = width / 2;
        int centerY = height / 2;
        graphics.drawString(text, centerX, centerY - (int) bounds.getHeight());
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        progress++;
        firePropertyChange("tick", 0, 1);
        if (progress >= max)
            timer.stop();
    }

    @Override
    public void applyPropertyChange(PropertyChangeEvent event, JLayer layer) {
        if ("tick".equals(event.getPropertyName()))
            layer.repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(200, 200);
        JPanel panel = new JPanel();
        panel.setBackground(Color.lightGray);
        frame.add(new JLayer<>(panel, new ProgressIndicator()));
        frame.setVisible(true);
    }
}
