package bad.robot.radiate.ui;

import javax.swing.*;

import static java.awt.Frame.MAXIMIZED_BOTH;

class FullScreen extends ScreenMode {

    private final JFrame frame;

    public FullScreen(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public DesktopMode switchTo() {
        frame.setExtendedState(frame.getExtendedState() | MAXIMIZED_BOTH);
        return new DesktopMode(frame);
    }
}
