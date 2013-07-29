package bad.robot.radiate.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.VK_F11;
import static java.awt.event.KeyEvent.VK_M;

class MaximiseToggle extends KeyAdapter {

    private final JFrame frame;

    public MaximiseToggle(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == VK_F11 || e.getKeyChar() == VK_M) {
            if (frame.getExtendedState() == Frame.NORMAL)
                frame.setExtendedState(frame.getExtendedState() | Frame.MAXIMIZED_BOTH);
            else
                frame.setExtendedState(Frame.NORMAL);
        }
    }
}
