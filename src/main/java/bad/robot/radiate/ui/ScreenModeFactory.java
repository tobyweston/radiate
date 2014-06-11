package bad.robot.radiate.ui;

import java.awt.*;

@FunctionalInterface
public interface ScreenModeFactory {

    static ScreenModeFactory fullScreen() {
        return bounds -> new FullScreen(bounds);
    }

    static ScreenModeFactory desktopScreen() {
        return bounds -> new DesktopMode(bounds);
    }

    ScreenMode create(Rectangle bounds);

}
