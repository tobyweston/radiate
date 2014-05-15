package bad.robot.radiate.ui;

import bad.robot.radiate.monitor.Observer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Stream;

import static bad.robot.radiate.ui.Screen.primaryScreen;
import static java.awt.Frame.NORMAL;

class StatusFrames implements Iterable<StatusFrame> {

    private final java.util.List<StatusFrame> frames = new ArrayList<>();

    StatusFrames() {
        Screen screen = primaryScreen();
        frames.add(new StatusFrame(screen));
        frames.add(new StatusFrame(screen.next()));
        // todo one frame per screen
    }

    StatusFrame primary() {
        return frames.get(0);
    }

    @Override
    public Iterator<StatusFrame> iterator() {
        return frames.iterator();
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

    public boolean inDesktopMode() {
        return frames.stream().anyMatch(frame -> frame.getExtendedState() == NORMAL);
    }
}
