package bad.robot.radiate.ui;

import bad.robot.radiate.Logging;
import bad.robot.radiate.Status;
import bad.robot.radiate.monitor.Information;
import bad.robot.radiate.monitor.Observable;
import bad.robot.radiate.monitor.Observer;
import bad.robot.radiate.teamcity.SanitisedException;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import static java.awt.AWTEvent.KEY_EVENT_MASK;
import static java.awt.Color.darkGray;
import static java.lang.String.format;
import static javax.swing.UIManager.getSystemLookAndFeelClassName;

public class SwingUi extends JFrame implements Ui, Observer {

    private final Set<StatusPanel> panels = new HashSet<>();
    private final Console console;

    static {
        Logging.initialise();
    }

    public SwingUi() throws HeadlessException {
        setLayout(new ChessboardLayout(panels));
        setupWindowing();
        console = new Console(this);
        setupEventListeners();
    }

    private void setupWindowing() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLookAndFeel();
        setTitle("Radiate");
        getContentPane().setBackground(darkGray);
        setSize(700, 500);
//        setUndecorated(true);
//        setExtendedState(getExtendedState() | MAXIMIZED_BOTH);
    }

    private void setupEventListeners() {
        getToolkit().addAWTEventListener(new ExitOnEscape(), KEY_EVENT_MASK);
        getToolkit().addAWTEventListener(new MaximiseToggle(this), KEY_EVENT_MASK);
        getToolkit().addAWTEventListener(new ToggleConsoleDialog(console), KEY_EVENT_MASK);
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

    @Override
    public void update(Status status) {
        // ignore status updates
    }

    @Override
    public void update(final Observable observable, final Exception exception) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                console.append(format("%s %s", new SanitisedException(exception).getMessage(), observable == null ? "" : observable.toString()));
                console.setVisible(true);
            }
        });
    }

    @Override
    public void update(Observable observable, final Information information) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                console.append(format("%s", information));
            }
        });
    }

}