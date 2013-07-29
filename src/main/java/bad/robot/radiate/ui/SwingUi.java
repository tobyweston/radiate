package bad.robot.radiate.ui;

import bad.robot.radiate.Status;

import javax.swing.*;
import java.awt.*;

import static bad.robot.radiate.Status.Unknown;

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
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
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

    public static void main(String... args) {
        new SwingUi().start();
    }

}
