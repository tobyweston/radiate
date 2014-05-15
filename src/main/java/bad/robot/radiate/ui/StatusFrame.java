package bad.robot.radiate.ui;

import bad.robot.radiate.monitor.Observer;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

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
        setSize(bounds.width, bounds.height);
        setLocation(bounds.x, bounds.y);
        getContentPane().setBackground(darkGray);
        setUndecorated(true);

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

}
