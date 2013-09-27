package bad.robot.radiate.ui;

import javax.swing.*;

import static bad.robot.radiate.ui.DesktopMode.desktopMode;
import static bad.robot.radiate.ui.FullScreen.fullScreen;
import static java.awt.Frame.NORMAL;

abstract class ScreenMode {

    static ScreenMode create(JFrame frame) {
        if (frame.getExtendedState() == NORMAL)
            return fullScreen(frame);
        return desktopMode(frame);
    }

    abstract ScreenMode switchTo();

}
