package bad.robot.radiate.ui;

import bad.robot.radiate.monitor.Observer;

import javax.swing.*;
import java.util.HashSet;
import java.util.Set;

import static java.lang.String.*;

@Deprecated
public class StatusFrame extends JFrame {

    private final Set<StatusPanel> panels = new HashSet<>();

    public StatusFrame(int index, ScreenMode screen) {
        setLayout(new ChessboardLayout(panels));
        setupWindowing(index, screen);
    }

    private void setupWindowing(int index, ScreenMode screen) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle(format("Radiate:%d", index));
        screen.accept(this);
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
