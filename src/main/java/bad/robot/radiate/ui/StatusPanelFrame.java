package bad.robot.radiate.ui;

import bad.robot.radiate.monitor.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.Set;

import static java.awt.Color.darkGray;
import static java.awt.GraphicsEnvironment.*;

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
        setSize(getDimensionForAllScreens());
//        primaryScreen().moveTo(this);
//        fullScreen(this).switchTo();
    }

    public Dimension getDimensionForAllScreens() {
        Rectangle2D totalScreenSize = new Rectangle2D.Double();
        for (GraphicsDevice screens : getLocalGraphicsEnvironment().getScreenDevices()) {
            for (GraphicsConfiguration graphics : screens.getConfigurations()) {
                totalScreenSize.union(totalScreenSize, graphics.getBounds(), totalScreenSize);
            }
        }
        return new Dimension((int) totalScreenSize.getWidth(), (int) totalScreenSize.getHeight());
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
