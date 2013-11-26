package bad.robot.radiate.ui;

import bad.robot.radiate.Activity;
import bad.robot.radiate.Logging;
import bad.robot.radiate.Status;
import bad.robot.radiate.monitor.Information;
import bad.robot.radiate.monitor.Observable;
import bad.robot.radiate.monitor.Observer;
import bad.robot.radiate.teamcity.Progress;
import bad.robot.radiate.teamcity.SanitisedException;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import static bad.robot.radiate.ui.FullScreen.fullScreen;
import static bad.robot.radiate.ui.Screen.primaryScreen;
import static java.awt.AWTEvent.KEY_EVENT_MASK;
import static java.awt.Color.darkGray;
import static java.lang.String.format;
import static javax.swing.SwingUtilities.invokeLater;
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
        setUndecorated(true);
        setSize(700, 500);
        primaryScreen().moveTo(this);
        fullScreen(this).switchTo();
    }

    private void setupEventListeners() {
        getToolkit().addAWTEventListener(new ExitOnEscape(), KEY_EVENT_MASK);
        getToolkit().addAWTEventListener(new MaximiseToggle(this), KEY_EVENT_MASK);
        getToolkit().addAWTEventListener(new ToggleConsoleDialog(console), KEY_EVENT_MASK);
        getToolkit().addAWTEventListener(new MoveMonitors(this), KEY_EVENT_MASK);
    }

    public Observer createStatusPanel() {
        StatusPanel panel = new StatusPanel(this, panels.size() + 1);
        panels.add(panel);
        return panel;
    }

    @Override
    public void start() {
        if (panels.isEmpty())
            createStatusPanel();
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
    public void update(final Observable source, final Status status) {
        // ignore status updates
    }

    @Override
    public void update(Observable source, Activity activity, Progress progress) {
        // ignore activity updates
    }

    @Override
    public void update(Observable source, final Information information) {
        invokeLater(new Runnable() {
            @Override
            public void run() {
                console.append(format("%s", information));
            }
        });
    }

    @Override
    public void update(final Observable source, final Exception exception) {
        invokeLater(new Runnable() {
            @Override
            public void run() {
                console.append(format("%s when monitoring %s", new SanitisedException(exception).getMessage(), source == null ? "" : source.toString()));
                if (inDesktopMode())
                    console.setVisible(true);
            }

            private boolean inDesktopMode() {
                return getExtendedState() == NORMAL;
            }
        });
    }

}