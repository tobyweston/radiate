package bad.robot.radiate.ui;

import bad.robot.radiate.monitor.Observer;

import java.util.ArrayList;
import java.util.stream.Stream;

import static java.awt.Frame.NORMAL;

class StatusFrames {

    private final java.util.List<StatusFrame> frames = new ArrayList<>();

    StatusFrames(FrameFactory frames) {
        this.frames.addAll(frames.create());
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

}
