package bad.robot.radiate.ui;

import javax.swing.*;

import static java.awt.Frame.NORMAL;

class DesktopMode extends ScreenMode {

    private final JFrame frame;

    public DesktopMode(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public FullScreen switchTo() {
        frame.setExtendedState(NORMAL);
        return new FullScreen(frame);
    }
}
