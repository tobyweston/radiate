package bad.robot.radiate.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.VK_ESCAPE;

class ExitOnEscape extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == VK_ESCAPE)
            System.exit(0);
    }
}
