package bad.robot.radiate.ui;

import bad.robot.radiate.Activity;
import bad.robot.radiate.Logging;
import bad.robot.radiate.Progress;
import bad.robot.radiate.Status;
import bad.robot.radiate.monitor.Information;
import bad.robot.radiate.monitor.Observable;
import bad.robot.radiate.monitor.Observer;
import bad.robot.radiate.teamcity.SanitisedException;

import javax.swing.*;
import java.awt.*;

import static java.awt.AWTEvent.KEY_EVENT_MASK;
import static java.lang.String.format;
import static javax.swing.SwingUtilities.invokeLater;
import static javax.swing.UIManager.getSystemLookAndFeelClassName;

public class SwingUi implements Ui, Observer {

    private final StatusPanelFrame container;
    private final Console console;

    static {
        Logging.initialise();
    }

    public SwingUi() {
        container = new StatusPanelFrame();
        console = new Console(container);
        setupGlobalEventListeners();
        setLookAndFeel();
    }

    private void setupGlobalEventListeners() {
        Toolkit.getDefaultToolkit().addAWTEventListener(new ExitOnEscape(), KEY_EVENT_MASK);
        Toolkit.getDefaultToolkit().addAWTEventListener(new MaximiseToggle(container), KEY_EVENT_MASK);
        Toolkit.getDefaultToolkit().addAWTEventListener(new ToggleConsoleDialog(console), KEY_EVENT_MASK);
        Toolkit.getDefaultToolkit().addAWTEventListener(new MoveMonitors(container), KEY_EVENT_MASK);
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
        container.display();
    }

    public Observer createStatusPanel() {
        return container.createStatusPanel();
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
        invokeLater(() -> console.append(format("%s", information)));
    }

    @Override
    public void update(final Observable source, final Exception exception) {
        invokeLater(() -> {
            console.append(format("%s when monitoring %s", new SanitisedException(exception).getMessage(), source == null ? "" : source.toString()));
            if (container.inDesktopMode())
                console.setVisible(true);
        });
    }

}