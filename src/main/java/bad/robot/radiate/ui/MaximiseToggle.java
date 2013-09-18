package bad.robot.radiate.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.VK_F11;
import static java.awt.event.KeyEvent.VK_M;

class MaximiseToggle extends KeyAdapter implements AWTEventListener {

    private final JFrame frame;
    private ScreenMode mode;

    public MaximiseToggle(JFrame frame) {
        this.frame = frame;
        mode = ScreenMode.create(frame);
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if (event.getKeyCode() == VK_F11 || event.getKeyCode() == VK_M)
            mode = mode.switchTo();
    }

    @Override
    public void eventDispatched(AWTEvent event) {
        if (event.getID() == KeyEvent.KEY_PRESSED)
            keyPressed((KeyEvent) event);
    }

}
