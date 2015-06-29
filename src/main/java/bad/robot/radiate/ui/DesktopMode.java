package bad.robot.radiate.ui;

import javax.swing.*;
import java.awt.*;

import static java.awt.Color.darkGray;

@Deprecated
/* aka resizable screen mode */
class DesktopMode implements ScreenMode {
    private final Rectangle bounds;

    public DesktopMode(Rectangle bounds) {
        this.bounds = bounds;
    }

    @Override
    public void accept(JFrame frame) {
        frame.setSize((int) (bounds.width * 0.80), (int) (bounds.height * 0.80));
        frame.setLocation(bounds.x, bounds.y);
        frame.getContentPane().setBackground(darkGray);
    }
}
