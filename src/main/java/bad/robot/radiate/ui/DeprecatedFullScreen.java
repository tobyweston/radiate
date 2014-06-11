package bad.robot.radiate.ui;

import javax.swing.*;

import static bad.robot.radiate.ui.DeprecatedDesktopMode.desktopMode;
import static java.awt.Frame.MAXIMIZED_BOTH;

@Deprecated
class DeprecatedFullScreen extends DeprecatedScreenMode {

    private final JFrame frame;

    public static DeprecatedFullScreen fullScreen(JFrame frame) {
        return new DeprecatedFullScreen(frame);
    }

    private DeprecatedFullScreen(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public DeprecatedDesktopMode switchTo() {
        frame.setExtendedState(frame.getExtendedState() | MAXIMIZED_BOTH);
        return desktopMode(frame);
    }
}
