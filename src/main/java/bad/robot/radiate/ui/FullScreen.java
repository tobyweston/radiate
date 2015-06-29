package bad.robot.radiate.ui;

import javax.swing.*;
import java.awt.*;

import static java.awt.Color.darkGray;

@Deprecated
class FullScreen implements ScreenMode {
    private final Rectangle bounds;

    public FullScreen(Rectangle bounds) {
        this.bounds = bounds;
    }

    @Override
    public void accept(JFrame frame) {
        frame.setSize(bounds.width, bounds.height);
        frame.setLocation(bounds.x, bounds.y);
        frame.getContentPane().setBackground(darkGray);
        frame.setUndecorated(true);
    }
}
