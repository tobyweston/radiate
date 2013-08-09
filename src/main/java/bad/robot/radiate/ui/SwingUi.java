package bad.robot.radiate.ui;

import bad.robot.radiate.monitor.Observer;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import static java.awt.AWTEvent.KEY_EVENT_MASK;
import static java.awt.Color.darkGray;
import static javax.swing.UIManager.getSystemLookAndFeelClassName;

public class SwingUi extends JFrame implements Ui {

    private final Set<StatusPanel> panels = new HashSet<>();

    public SwingUi() throws HeadlessException {
        setLayout(new ChessboardLayout(panels));
        setupWindowing();
        setupEventListeners();
    }

    private void setupWindowing() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLookAndFeel();
        setTitle("Radiate");
        getContentPane().setBackground(darkGray);
        setSize(400, 300);
//        setUndecorated(true);
//        setExtendedState(getExtendedState() | MAXIMIZED_BOTH);
    }

    private void setupEventListeners() {
        getToolkit().addAWTEventListener(new ExitOnEscape(), KEY_EVENT_MASK);
        getToolkit().addAWTEventListener(new MaximiseToggle(this), KEY_EVENT_MASK);
    }

    public Observer createStatusPanel() {
        StatusPanel panel = new StatusPanel(this);
        panels.add(panel);
        return panel;
    }

    @Override
    public void start() {
        setVisible(true);
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(getSystemLookAndFeelClassName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}