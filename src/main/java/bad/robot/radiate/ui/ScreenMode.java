package bad.robot.radiate.ui;

import javax.swing.*;

import static java.awt.Frame.NORMAL;

abstract class ScreenMode {

    static ScreenMode create(JFrame frame) {
        if (frame.getExtendedState() == NORMAL)
            return new FullScreen(frame);
        return new DesktopMode(frame);
    }

    abstract ScreenMode switchTo();

}
