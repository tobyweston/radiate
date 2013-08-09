package bad.robot.radiate.ui;

import bad.robot.radiate.Status;
import bad.robot.radiate.monitor.Observer;

import javax.swing.*;
import java.awt.*;

import static java.awt.AWTEvent.KEY_EVENT_MASK;
import static javax.swing.SwingUtilities.invokeLater;
import static javax.swing.UIManager.getSystemLookAndFeelClassName;

public class SwingUi extends JFrame implements Ui, Observer {

    private final StatusPanel statusPanel;

    public SwingUi() throws HeadlessException {
        statusPanel = new StatusPanel(this);
        setupWindowing();
        setupEventListeners();
    }

    private void setupWindowing() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLookAndFeel();
        setTitle("Radiate");
        setSize(400, 300);
        setUndecorated(true);
        setExtendedState(getExtendedState() | MAXIMIZED_BOTH);
    }

    private void setupEventListeners() {
        getToolkit().addAWTEventListener(new ExitOnEscape(), KEY_EVENT_MASK);
        getToolkit().addAWTEventListener(new MaximiseToggle(this), KEY_EVENT_MASK);
    }

    @Override
    public void start() {
        setVisible(true);
    }

    @Override
    public void update(final Status status) {
        invokeLater(new Runnable() {
            @Override
            public void run() {
                statusPanel.update(status);
            }
        });
    }

    @Override
    public void update(final Exception exception) {
        invokeLater(new Runnable() {
            @Override
            public void run() {
                statusPanel.update(exception);
            }
        });
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(getSystemLookAndFeelClassName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}