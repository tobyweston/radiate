package bad.robot.radiate.ui;

import bad.robot.radiate.monitor.Observer;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import static bad.robot.radiate.ui.FullScreen.fullScreen;
import static bad.robot.radiate.ui.Screen.primaryScreen;
import static java.awt.Color.darkGray;

public class StatusPanelFrame extends JFrame {

    private final Set<StatusPanel> panels = new HashSet<>();

    public StatusPanelFrame() throws HeadlessException {
        setLayout(new ChessboardLayout(panels));
        setupWindowing();
    }

    private void setupWindowing() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Radiate");
        getContentPane().setBackground(darkGray);
        setUndecorated(true);
        setSize(400, 400);
        primaryScreen().moveTo(this);
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

    public boolean inDesktopMode() {
        return getExtendedState() == NORMAL;
    }
}
