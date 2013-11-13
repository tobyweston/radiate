package bad.robot.radiate.ui;

import bad.robot.radiate.State;

import javax.swing.*;
import javax.swing.plaf.LayerUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.beans.PropertyChangeEvent;

import static bad.robot.radiate.State.Error;
import static java.awt.BasicStroke.CAP_ROUND;
import static java.awt.BasicStroke.JOIN_ROUND;
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
        int width = component.getWidth();
        int height = component.getHeight();

        Graphics2D graphics = (Graphics2D) g.create();
        drawProgressIndicator(width, height, graphics);
        graphics.dispose();
    }

    private void drawProgressIndicator(int width, int height, Graphics2D graphics) {
        int reductionPercentage = 20;
        int size = Math.min(width, height) / reductionPercentage;
        int x = width / 2;
        int y = height / 2;
        graphics.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
        graphics.setStroke(new BasicStroke(size / 4, CAP_ROUND, JOIN_ROUND));
        graphics.setPaint(white);

        if (progress <= max) {
            int angle = -(int) (((float) progress / max) * 360);
            graphics.fillArc(0, 0, width, height, 90, angle);
        }
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
        frame.setSize(400, 400);
        JPanel panel = new JPanel();
        frame.add(new JLayer<>(panel, new ProgressIndicator()));
        frame.setVisible(true);
    }
}
