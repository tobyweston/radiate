package bad.robot.radiate.ui;

import javax.swing.*;
import java.awt.*;

import static java.lang.String.format;
import static javax.swing.JFrame.DISPOSE_ON_CLOSE;

class ExceptionsDisplay extends TransparentDialog {

    private final JTextArea text = new TransparentTextArea();

    public ExceptionsDisplay(Frame owner) {
        super("", owner);
//        hideCloseButton();
        makeResizeable();
        getJDialog().setSize(400, 450);
        getJDialog().setLocationRelativeTo(owner);
        getJDialog().setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JScrollPane scroll = new TransparentJScrollPane(text);
        getJDialog().add(scroll);
    }

    public void append(String string) {
        text.append(format("%s\n", string));
    }
}
