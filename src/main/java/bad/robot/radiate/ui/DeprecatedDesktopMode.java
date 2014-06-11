package bad.robot.radiate.ui;

import javax.swing.*;

import static bad.robot.radiate.ui.DeprecatedFullScreen.fullScreen;
import static java.awt.Frame.NORMAL;

@Deprecated
class DeprecatedDesktopMode extends DeprecatedScreenMode {

    private final JFrame frame;

    private DeprecatedDesktopMode(JFrame frame) {
        this.frame = frame;
    }

    public static DeprecatedDesktopMode desktopMode(JFrame frame) {
        return new DeprecatedDesktopMode(frame);
    }

    @Override
    public DeprecatedFullScreen switchTo() {
        frame.setExtendedState(NORMAL);
        return fullScreen(frame);
    }
}
