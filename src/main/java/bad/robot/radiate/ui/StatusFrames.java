package bad.robot.radiate.ui;

import bad.robot.radiate.monitor.Observer;

import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Stream;

@Deprecated
class StatusFrames {

    private final java.util.List<StatusFrame> frames = new ArrayList<>();

    StatusFrames(FrameFactory frames) {
        this.frames.addAll(frames.create());
    }

    StatusFrame primary() {
        return frames.get(0);
    }

    public void display() {
        frames.forEach(StatusFrame::display);
    }

    public void dispose() {
        frames.forEach(Window::dispose);
    }

    public Stream<Observer> createStatusPanels() {
        return frames.stream().map(StatusFrame::createStatusPanel);
    }

}
