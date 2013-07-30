package bad.robot.radiate.ui;

import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.VK_ESCAPE;

class ExitOnEscape extends KeyAdapter implements AWTEventListener {
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == VK_ESCAPE)
            System.exit(0);
    }

    @Override
    public void eventDispatched(AWTEvent event) {
        keyPressed((KeyEvent) event);
    }
}
