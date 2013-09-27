package bad.robot.radiate.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;

class MoveMonitors extends KeyAdapter implements AWTEventListener {

    private final JFrame frame;
    private Screen screen;

    public MoveMonitors(JFrame frame) {
        this.frame = frame;
        this.screen = Screen.primaryScreen();
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if (event.getKeyCode() == VK_RIGHT) {
            screen = screen.next();
            screen.moveTo(frame);
        } else if (event.getKeyCode() == VK_LEFT) {
            screen = screen.previous();
            screen.moveTo(frame);
        }
    }

    @Override
    public void eventDispatched(AWTEvent event) {
        if (event.getID() == KeyEvent.KEY_PRESSED)
            keyPressed((KeyEvent) event);
    }

}
