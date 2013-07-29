package bad.robot.radiate.ui;

import javax.swing.*;
import java.awt.*;

import static bad.robot.radiate.Status.Unknown;

public class SwingUi extends JFrame implements Ui {

    public SwingUi() throws HeadlessException {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLookAndFeel();
        add(new StatusPanel(Unknown));
        setTitle("Hello");
        setSize(600, 400);
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start() {
        setVisible(true);
    }

    public static void main(String... args) {
        new SwingUi().start();
    }

}
