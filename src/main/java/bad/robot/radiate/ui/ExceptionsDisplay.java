package bad.robot.radiate.ui;

import javax.swing.*;
import java.awt.*;

import static java.lang.String.format;
import static javax.swing.JFrame.DISPOSE_ON_CLOSE;

class ExceptionsDisplay extends TransparentDialog {

    private final JTextArea text = new TransparentTextArea();

    public ExceptionsDisplay(Frame owner) {
        super("", owner);
        makeResizeable();
        JDialog dialog = getJDialog();
        dialog.setSize(calculateSize(owner));
        dialog.setLocationRelativeTo(owner);
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dialog.add(new TransparentJScrollPane(text));
    }

    private static Dimension calculateSize(Frame owner) {
        Dimension parent = owner.getSize();
        Dimension dimension = new Dimension(parent);
        dimension.setSize(shrink(parent.getWidth()), shrink(parent.getHeight()));
        return dimension;
    }

    private static double shrink(double dimension) {
        return dimension - (dimension / 10);
    }

    public void append(String string) {
        text.append(format("%s\n", string));
    }
}
