package bad.robot.radiate.ui;

import bad.robot.radiate.Status;

import javax.swing.*;
import java.awt.*;

import static bad.robot.radiate.Status.Unknown;
import static javax.swing.UIManager.getSystemLookAndFeelClassName;

public class SwingUi extends JFrame implements Ui {

    private final StatusPanel statusPanel = new StatusPanel(Unknown);

    public SwingUi() throws HeadlessException {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        setExtendedState(getExtendedState() | MAXIMIZED_BOTH);
        setLookAndFeel();
        addKeyListener(new ExitOnEscape());
        add(statusPanel);
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(getSystemLookAndFeelClassName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start() {
        setVisible(true);
    }

    @Override
    public void update(Status status) {
        statusPanel.update(status);
    }

}
