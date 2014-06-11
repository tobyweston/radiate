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
import java.awt.event.AWTEventListener;
import java.util.Arrays;
import java.util.stream.Stream;

import static bad.robot.radiate.MonitoringTypes.*;
import static java.awt.AWTEvent.KEY_EVENT_MASK;
import static java.awt.Toolkit.*;
import static java.awt.event.KeyEvent.*;
import static java.lang.String.format;
import static javax.swing.SwingUtilities.invokeLater;
import static javax.swing.UIManager.getSystemLookAndFeelClassName;

public class SwingUi implements Ui, Observer {

    private final StatusFrames frames;
    private final Console console;

    static {
        Logging.initialise();
    }

    public SwingUi(FrameFactory frames) {
        this.frames = new StatusFrames(frames);
        this.console = new Console(this.frames.primary());
        setupGlobalEventListeners();
        setLookAndFeel();
    }

    private static final SwitchScreenMode switchScreenMode = new SwitchScreenMode(FrameFactory::desktopMode, FrameFactory::fullScreen);

    private void setupGlobalEventListeners() {
        addAwtEventListener(new ExitOnEscape());
        addAwtEventListener(switchScreenMode);
        addAwtEventListener(new ToggleConsoleDialog(console));
        addAwtEventListener(new Restart(singleAggregate(), VK_A));
        addAwtEventListener(new Restart(multipleProjects(), VK_C));
        addAwtEventListener(new Restart(multipleBuildsDemo(), VK_D));
    }

    private void addAwtEventListener(AWTEventListener listener) {
        getDefaultToolkit().addAWTEventListener(listener, KEY_EVENT_MASK);
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
        frames.display();
    }

    public void stop() {
        frames.dispose();
        Arrays.stream(getDefaultToolkit().getAWTEventListeners()).forEach(listener -> getDefaultToolkit().removeAWTEventListener(listener));
    }

    public Stream<Observer> createStatusPanels() {
        return frames.createStatusPanels();
    }

    @Override
    public void update(Observable source, Status status) {
        // ignore status updates
    }

    @Override
    public void update(Observable source, Activity activity, Progress progress) {
        // ignore activity updates
    }

    @Override
    public void update(Observable source, Information information) {
        invokeLater(() -> console.append(format("%s", information)));
    }

    @Override
    public void update(Observable source, Exception exception) {
        invokeLater(() -> console.append(format("%s when monitoring %s", new SanitisedException(exception).getMessage(), source == null ? "" : source.toString())));
    }

}