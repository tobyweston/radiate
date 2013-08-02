package bad.robot.radiate.ui;

import javax.swing.*;
import javax.swing.plaf.LayerUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

import static bad.robot.radiate.ui.FrameRate.videoFramesPerSecond;
import static java.awt.AlphaComposite.SRC_OVER;
import static java.awt.AlphaComposite.getInstance;
import static java.awt.BasicStroke.CAP_ROUND;
import static java.awt.BasicStroke.JOIN_ROUND;
import static java.awt.Color.white;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;

class BusyIndicator extends LayerUI<JPanel> implements ActionListener {

    private boolean running;
    private boolean fadingOut;
    private Timer timer;

    private int angle;
    private int fadeCount;
    private int fadeLimit = 15;

    @Override
    public void paint(Graphics g, JComponent component) {
        int width = component.getWidth();
        int height = component.getHeight();
        super.paint(g, component);
        if (!running)
            return;
        Graphics2D graphics = (Graphics2D) g.create();
        float fade = (float) fadeCount / (float) fadeLimit;
        fadeOut(width, height, graphics, fade);
        drawBusyIndicator(width, height, graphics, fade);
        graphics.dispose();
    }

    private void drawBusyIndicator(int width, int height, Graphics2D graphics, float fade) {
        int reductionPercentage = 20;
        int size = Math.min(width, height) / reductionPercentage;
        int x = width / 2;
        int y = height / 2;
        graphics.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
        graphics.setStroke(new BasicStroke(size / 4, CAP_ROUND, JOIN_ROUND));
        graphics.setPaint(white);
        graphics.rotate(Math.PI * angle / 180, x, y);
        for (int i = 0; i < 12; i++) {
            float scale = (11.0f - (float) i) / 11.0f;
            graphics.drawLine(x + size, y, x + size * 2, y);
            graphics.rotate(-Math.PI / 6, x, y);
            graphics.setComposite(getInstance(SRC_OVER, scale * fade));
        }
    }

    private void fadeOut(int width, int height, Graphics2D graphics, float fade) {
        Composite urComposite = graphics.getComposite();
        float alpha = .3f * fade;
        graphics.setComposite(getInstance(SRC_OVER, alpha));
        graphics.fillRect(0, 0, width, height);
        graphics.setComposite(urComposite);
    }

    public void start() {
        if (running)
            return;
        running = true;
        fadingOut = false;
        fadeCount = 0;
        timer = new Timer(videoFramesPerSecond.asFrequencyInMillis(), this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            firePropertyChange("tick", 0, 1);
            angle += 3;
            if (angle >= 360) {
                angle = 0;
            }
            if (fadingOut) {
                if (--fadeCount == 0) {
                    running = false;
                    timer.stop();
                }
            } else if (fadeCount < fadeLimit) {
                fadeCount++;
            }
        }
    }

    @Override
    public void applyPropertyChange(PropertyChangeEvent event, JLayer layer) {
        if ("tick".equals(event.getPropertyName()))
            layer.repaint();
    }

    public void stop() {
        fadingOut = true;
    }
}
