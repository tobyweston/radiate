package bad.robot.radiate.ui;

import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.VK_F1;
import static java.awt.event.KeyEvent.VK_I;

class ToggleInformationDialog extends KeyAdapter implements AWTEventListener {

    private final TransparentDialog dialog;

    public ToggleInformationDialog(TransparentDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if (event.getKeyCode() == VK_F1 || event.getKeyCode() == VK_I)
            dialog.setVisible(!dialog.isVisible());
    }

    @Override
    public void eventDispatched(AWTEvent event) {
        if (event.getID() == KeyEvent.KEY_PRESSED)
            keyPressed((KeyEvent) event);
    }
}
