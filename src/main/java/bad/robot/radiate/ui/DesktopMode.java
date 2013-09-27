package bad.robot.radiate.ui;

import javax.swing.*;

import static bad.robot.radiate.ui.FullScreen.fullScreen;
import static java.awt.Frame.NORMAL;

class DesktopMode extends ScreenMode {

    private final JFrame frame;

    private DesktopMode(JFrame frame) {
        this.frame = frame;
    }

    public static DesktopMode desktopMode(JFrame frame) {
        return new DesktopMode(frame);
    }

    @Override
    public FullScreen switchTo() {
        frame.setExtendedState(NORMAL);
        return fullScreen(frame);
    }
}
