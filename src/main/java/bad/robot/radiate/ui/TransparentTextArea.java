package bad.robot.radiate.ui;

import javax.swing.*;
import java.awt.*;

import static java.awt.Font.BOLD;

class TransparentTextArea extends JTextArea {

    TransparentTextArea() {
        setOpaque(false);
        setFont(UIManager.getFont("Button.font").deriveFont(BOLD, 11.0f));
        setForeground(Color.white);
        setBackground(Color.black);
        setLineWrap(true);
        setWrapStyleWord(true);
    }
}
