package bad.robot.radiate.ui;

import bad.robot.radiate.monitor.Observer;
import bad.robot.radiate.ui.swing.Region;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.awt.Color.darkGray;
import static java.lang.String.*;

public class StatusFrame extends JFrame {

    private final Set<StatusPanel> panels = new HashSet<>();

    public StatusFrame(int index, Rectangle bounds) {
        setLayout(new ChessboardLayout(panels));
        setupWindowing(index, bounds);
    }

    private void setupWindowing(int index, Rectangle bounds) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle(format("Radiate:%d", index));

        new FullScreen(bounds).accept(this);

//        screen.moveTo(this);
//        fullScreen(this).switchTo();
    }

    public Observer createStatusPanel() {
        StatusPanel panel = new StatusPanel(this, panels.size() + 1);
        panels.add(panel);
        return panel;
    }

    public void display() {
        if (panels.isEmpty())
            createStatusPanel();
        setVisible(true);
    }

    private static class FullScreen implements Consumer<JFrame> {
        private final Rectangle bounds;

        public FullScreen(Rectangle bounds) {
            this.bounds = bounds;
        }

        @Override
        public void accept(JFrame frame) {
            frame.setSize(bounds.width, bounds.height);
            frame.setLocation(bounds.x, bounds.y);
            frame.getContentPane().setBackground(darkGray);
            frame.setUndecorated(true);
        }
    }

    // aka resizable screen mode
    private static class DesktopMode implements Consumer<JFrame> {
        private final Rectangle bounds;

        public DesktopMode(Rectangle bounds) {
            this.bounds = bounds;
        }

        @Override
        public void accept(JFrame frame) {
            frame.setSize((int) (bounds.width * 0.80), (int) (bounds.height * 0.80));
            frame.setLocation(bounds.x, bounds.y);
            frame.getContentPane().setBackground(darkGray);
        }
    }
}
