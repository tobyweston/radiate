package bad.robot.radiate.ui;

import javax.swing.*;

import static bad.robot.radiate.ui.DeprecatedDesktopMode.desktopMode;
import static bad.robot.radiate.ui.DeprecatedFullScreen.fullScreen;
import static java.awt.Frame.NORMAL;

@Deprecated
abstract class DeprecatedScreenMode {

    static DeprecatedScreenMode create(JFrame frame) {
        if (frame.getExtendedState() == NORMAL)
            return fullScreen(frame);
        return desktopMode(frame);
    }

    abstract DeprecatedScreenMode switchTo();

}
