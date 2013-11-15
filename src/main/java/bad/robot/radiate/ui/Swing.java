package bad.robot.radiate.ui;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.Callable;

import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import static java.awt.geom.AffineTransform.getScaleInstance;

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

    public static Point centerTextWithinRegion(Rectangle region, Graphics2D graphics, java.awt.Font font, String text) {
        FontMetrics metrics = graphics.getFontMetrics(font);
        Rectangle2D size = metrics.getStringBounds(text, graphics);
        double x = (region.width - size.getWidth()) / 2;
        double y = (region.height - size.getHeight()) / 2 + metrics.getAscent();
        return new Point((int) x, (int) y);
    }

    public static void setFontScaledToRegion(Rectangle region, Graphics2D graphics, String text, Font font) {
        FontMetrics metrics = graphics.getFontMetrics(font);
        float xScale = (float) (region.width / metrics.stringWidth(text));
        float yScale = (region.height / metrics.getHeight());
        float scale = determineWhichAccessToScaleOn(xScale, yScale);

        graphics.setFont(font.deriveFont(getScaleInstance(scale, scale)));
        graphics.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
    }

    private static float determineWhichAccessToScaleOn(float xScale, float yScale) {
        if (xScale > yScale)
            return yScale;
        return xScale;
    }

    public static Rectangle getReducedRegion(Rectangle parent, double amount) {
        Double height = parent.height / amount;
        Double width = parent.width / amount;
        return new Rectangle(parent.x, parent.y, width.intValue(), height.intValue());
    }

    public static void drawCentreLines(Rectangle region, Graphics2D graphics) {
        graphics.setStroke(new BasicStroke(1));
        graphics.drawLine(region.x, region.y, region.width, region.height);
        graphics.drawLine(region.width, region.y, region.x, region.height);
        graphics.drawLine(region.x, region.height / 2, region.width, region.height / 2);
        graphics.drawLine(region.x + region.width / 2, region.y, region.x + region.width / 2, region.height);
    }
}
