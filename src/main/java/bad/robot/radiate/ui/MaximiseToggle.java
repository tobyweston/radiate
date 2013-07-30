package bad.robot.radiate.ui;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static java.awt.Frame.MAXIMIZED_BOTH;
import static java.awt.Frame.NORMAL;
import static java.awt.event.KeyEvent.VK_F11;
import static java.awt.event.KeyEvent.VK_M;

class MaximiseToggle extends KeyAdapter {

    private final JFrame frame;

    public MaximiseToggle(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == VK_F11 || e.getKeyCode() == VK_M) {
            if (frame.getExtendedState() == NORMAL)
                frame.setExtendedState(frame.getExtendedState() | MAXIMIZED_BOTH);
            else
                frame.setExtendedState(NORMAL);
        }
    }
}
