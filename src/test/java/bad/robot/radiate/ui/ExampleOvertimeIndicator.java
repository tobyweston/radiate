package bad.robot.radiate.ui;

import bad.robot.radiate.Progress;
import bad.robot.radiate.ui.swing.Debug;

import javax.swing.*;
import java.awt.*;

import static bad.robot.radiate.Activity.Progressing;
import static bad.robot.radiate.ui.swing.Debug.drawCentreLines;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class ExampleOvertimeIndicator {

    public static void main(String[] args) throws InterruptedException {
        OvertimeIndicator indicator = setupWindow();
        indicator.setVisiblityBasedOn(Progressing, new Progress(100, 100));
    }

    private static OvertimeIndicator setupWindow() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        JPanel panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D graphics = (Graphics2D) g.create();
                drawCentreLines(this.getBounds(), graphics);
                graphics.dispose();
            }
        };
        panel.setBackground(Color.lightGray);
        OvertimeIndicator indicator = new OvertimeIndicator();
        frame.add(new JLayer<>(panel, indicator));
        frame.setVisible(true);
        return indicator;
    }

}
