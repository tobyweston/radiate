package bad.robot.radiate.ui;

import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;

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
        this.screens = getScreenDevicesSortedByLocation();
        this.maxIndex = screens.length - 1;
        if (index > maxIndex)
            throw new IllegalArgumentException(String.format("can not change to a screen '%d', there are only '%d'", index, maxIndex));
        this.index = index;
    }

    private static int defaultScreen() {
        GraphicsDevice defaultScreen = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        GraphicsDevice[] screens = getScreenDevicesSortedByLocation();
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
        System.out.println("screen at index " + index);
        frame.setLocation(screens[index].getDefaultConfiguration().getBounds().x, frame.getY());
    }

    private static GraphicsDevice[] getScreenDevicesSortedByLocation() {
        GraphicsDevice[] screens = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        Arrays.sort(screens, byLocation());
        return screens;
    }

    private static Comparator<GraphicsDevice> byLocation() {
        return new Comparator<GraphicsDevice>() {
            public int compare(GraphicsDevice first, GraphicsDevice second) {
                Rectangle boundsOfFirst = first.getDefaultConfiguration().getBounds();
                Rectangle boundsOfSecond = second.getDefaultConfiguration().getBounds();
                int delta = boundsOfFirst.y - boundsOfSecond.y;
                if (delta == 0)
                    delta = boundsOfFirst.x - boundsOfSecond.x;
                return delta;
            }
        };
    }
}
