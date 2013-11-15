package bad.robot.radiate.ui;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.Callable;

public class Swing {

    public static void applyWithComposite(Graphics2D graphics, Composite composite, Callable<Void> callable) {
        Composite original = graphics.getComposite();
        graphics.setComposite(composite);
        try {
            callable.call();
        } catch (Exception e) {
            // ignore
        } finally {
            graphics.setComposite(original);
        }
    }

    public static Point centerTextWithinRegion(Rectangle region, Graphics2D graphics, Font font, String text) {
        FontMetrics metrics = graphics.getFontMetrics(font);
        Rectangle2D size = metrics.getStringBounds(text, graphics);
        double x = (region.width - size.getWidth()) / 2;
        double y = (region.height - size.getHeight()) / 2 + metrics.getAscent();
        return new Point((int) x, (int) y);
    }

}
