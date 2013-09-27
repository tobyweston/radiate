package bad.robot.radiate.ui;

import java.awt.*;

class Screen {
    private final int index;
    private final int maxIndex;
    private final GraphicsDevice[] screens;

    static Screen primaryScreen() {
        return new Screen();
    }

    private Screen() {
        this(defaultScreen());
    }

    private Screen(int index) {
        this.screens = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        this.maxIndex = screens.length - 1;
        if (index > maxIndex)
            throw new IllegalArgumentException(String.format("can not change to a screen '%d', there are only '%d'", index, maxIndex));
        this.index = index;
    }

    private static int defaultScreen() {
        GraphicsDevice defaultScreen = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        GraphicsDevice[] screens = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        for (int index = 0; index < screens.length; index++) {
            if (screens[index] == defaultScreen)
                return index;
        }
        return 0;
    }

    public Screen next() {
        if (index < maxIndex)
            return new Screen(index + 1);
        return new Screen(0);
    }

    public Screen previous() {
        if (index == 0)
            return new Screen(maxIndex);
        return new Screen(index - 1);
    }

    public void moveTo(Frame frame) {
        System.out.println("moving to screen at index " + index);
        frame.setLocation(screens[index].getDefaultConfiguration().getBounds().x, frame.getY());
    }
}
