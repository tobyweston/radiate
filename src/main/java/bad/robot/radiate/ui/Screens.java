package bad.robot.radiate.ui;

import java.util.Objects;
import java.util.function.Supplier;

class Screens {

    private final Supplier<ScreenModeFactory>[] screens;
    private int index;

    public Screens(Supplier<ScreenModeFactory>... screens) {
        if (screens.length == 0)
            throw new IllegalArgumentException();
        this.screens = screens;
    }

    ScreenModeFactory next() {
        if (index == screens.length)
            index = 0;
        return screens[index++].get();
    }
}
