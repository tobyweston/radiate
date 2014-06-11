package bad.robot.radiate.ui;

import javax.swing.*;

import static bad.robot.radiate.ui.DesktopMode.desktopMode;
import static java.awt.Frame.MAXIMIZED_BOTH;

@Deprecated
class FullScreen extends ScreenMode {

    private final JFrame frame;

    public static FullScreen fullScreen(JFrame frame) {
        return new FullScreen(frame);
    }

    private FullScreen(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public DesktopMode switchTo() {
        frame.setExtendedState(frame.getExtendedState() | MAXIMIZED_BOTH);
        return desktopMode(frame);
    }
}
