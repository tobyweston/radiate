package bad.robot.radiate.ui;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import static java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

@Deprecated
public interface FrameFactory {

    Collection<StatusFrame> create();

    static FrameFactory fullScreen() {
        return () -> {
            GraphicsDevice[] screens = getLocalGraphicsEnvironment().getScreenDevices();
            Stream<StatusFrame> frames = range(0, screens.length).mapToObj(index -> new StatusFrame(index, new FullScreen(screens[index].getDefaultConfiguration().getBounds())));
            return frames.collect(toList());
        };
    }

    static FrameFactory desktopMode() {
        return () -> {
            List<StatusFrame> frames = new ArrayList<>(1);
            Rectangle bounds = getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().getBounds();
            frames.add(new StatusFrame(0, new DesktopMode(bounds)));
            return frames;
        };
    }
}
