package bad.robot.radiate.ui;

import bad.robot.radiate.monitor.Observer;

import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Stream;

import static java.awt.Frame.NORMAL;
import static java.util.stream.IntStream.range;

class StatusFrames {

    private final java.util.List<StatusFrame> frames = new ArrayList<>();

    StatusFrames(ScreenModeFactory screen) {
        GraphicsDevice[] screens = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        range(0, screens.length).forEach(index -> {
            frames.add(new StatusFrame(index, screen.create(screens[index].getDefaultConfiguration().getBounds())));
        });
    }

    StatusFrame primary() {
        return frames.get(0);
    }

    public void display() {
        frames.forEach(frame -> frame.display());
    }

    public void dispose() {
        frames.forEach(frame -> frame.dispose());
    }

    public Stream<Observer> createStatusPanels() {
        return frames.stream().map(frame -> frame.createStatusPanel());
    }

    @Deprecated
    public boolean inDesktopMode() {
        return frames.stream().anyMatch(frame -> frame.getExtendedState() == NORMAL);
    }
}
