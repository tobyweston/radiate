package bad.robot.radiate.ui;

import bad.robot.radiate.Progress;
import bad.robot.radiate.ui.swing.Debug;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static bad.robot.radiate.Activity.Progressing;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

@Deprecated
public class ExampleProgressIndicator {

    public static void main(String[] args) throws InterruptedException {
        ProgressIndicator indicator = setupWindow();
        updateProgressInAThread(indicator);
            Thread.sleep(8000);
            updateProgressInAThread(indicator);
//            Thread.sleep(12000);
//            updateProgressInAThread(indicator);
    }

    private static ProgressIndicator setupWindow() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        JPanel panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D graphics = (Graphics2D) g.create();
                Debug.drawCentreLines(this.getBounds(), graphics);
                graphics.dispose();
            }
        };
        panel.setBackground(Color.lightGray);
        ProgressIndicator indicator = new ProgressIndicator();
        frame.add(new JLayer<>(panel, indicator));
        frame.setVisible(true);
        return indicator;
    }

    private static void updateProgressInAThread(final ProgressIndicator indicator) {
        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(1);
        final Integer[] progress = {0};
        threadPool.scheduleAtFixedRate(() -> {
			boolean goneBackwards = false;
			progress[0] = progress[0] + 50;
            if (progress[0] > 50 && !goneBackwards)
				goneBackwards = true;
//                    if (goneBackwards)
//                        progress[0] = progress[0] = 16;
			if (progress[0] <= 100)
				indicator.setVisibilityBasedOn(Progressing, new Progress(progress[0], ProgressIndicator.maximum));
		}, 1, 1, TimeUnit.SECONDS);
    }
}
