package bad.robot.radiate.ui;

import bad.robot.radiate.monitor.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.Set;

import static bad.robot.radiate.ui.FullScreen.fullScreen;
import static java.awt.Color.darkGray;
import static java.awt.GraphicsEnvironment.*;

public class StatusFrame extends JFrame {

    private final Set<StatusPanel> panels = new HashSet<>();

    public StatusFrame(Screen screen) throws HeadlessException {
        setLayout(new ChessboardLayout(panels));
        setupWindowing(screen);
    }

    private void setupWindowing(Screen screen) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Radiate");
        getContentPane().setBackground(darkGray);
        setUndecorated(true);
        screen.moveTo(this);
        fullScreen(this).switchTo();
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

}
