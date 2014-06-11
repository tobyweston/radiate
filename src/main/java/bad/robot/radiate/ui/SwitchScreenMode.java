package bad.robot.radiate.ui;

import bad.robot.radiate.CircularArrayBuffer;

import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.Supplier;

import static bad.robot.radiate.Main.Radiate;
import static bad.robot.radiate.MonitoringTypes.singleAggregate;
import static java.awt.event.KeyEvent.VK_F11;
import static java.awt.event.KeyEvent.VK_M;

class SwitchScreenMode extends KeyAdapter implements AWTEventListener {

    private final CircularArrayBuffer<FrameFactory> screens;

    public SwitchScreenMode(Supplier<FrameFactory>... screens) {
        this.screens = new CircularArrayBuffer(screens);
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if (event.getKeyCode() == VK_F11 || event.getKeyCode() == VK_M)
            Radiate.stop();
        Radiate.start(singleAggregate(), screens.next()); // TODO make singleAggregate() the current one, don't hardcode it
    }

    @Override
    public void eventDispatched(AWTEvent event) {
        if (event.getID() == KeyEvent.KEY_PRESSED)
            keyPressed((KeyEvent) event);
    }

}