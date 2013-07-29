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
        addKeyListener(new MaximiseToggle(this));
        add(statusPanel);
        setSize(800, 600);
    }

    @Override
    public void start() {
        setVisible(true);
    }

    @Override
    public void update(Status status) {
        statusPanel.update(status);
    }

    @Override
    public void update(Exception exception) {
        statusPanel.update(exception);
    }


    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(getSystemLookAndFeelClassName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
