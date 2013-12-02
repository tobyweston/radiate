package bad.robot.radiate.ui;

import bad.robot.http.configuration.AbstractValueType;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.Callable;

import static java.awt.BasicStroke.CAP_BUTT;
import static java.awt.BasicStroke.JOIN_MITER;
import static java.awt.Color.darkGray;
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
        Rectangle2D textSize = metrics.getStringBounds(text, graphics);
        double x = region.x + (region.width - textSize.getWidth()) / 2;
        double y = region.y + (region.height - textSize.getHeight()) / 2 + metrics.getAscent();
        return new Point((int) Math.abs(x), (int) Math.abs(y));
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

    public static Rectangle getReducedRegion(Rectangle region, Percentage percentage) {
        Double width = percentage.of(region.width);
        Double height = percentage.of(region.height);
        return new Rectangle(region.x, region.y, width.intValue(), height.intValue());
    }

    public static Rectangle getReducedRegionAsSquare(Component component, Percentage percentage) {
        Rectangle region = new Rectangle(component.getX(), component.getY(), component.getWidth(), component.getHeight());
        Double width = percentage.of(Math.min(region.width, region.height));
        Double height = width;
        return new Rectangle(region.x, region.y, width.intValue(), height.intValue());
    }

    public static void centerRegionWithinComponent(Rectangle region, Component component) {
        int x = Math.abs(region.width - component.getWidth()) / 2 + region.x;
        int y = Math.abs(region.height - component.getHeight()) / 2 + region.y;
        if (y < region.y)
            y = region.y;
        region.setLocation(x, y);
    }

    public static void drawCentreLines(Rectangle region, Graphics2D graphics) {
        drawCentreLines(region, graphics, darkGray);
    }

    public static void drawCentreLines(Rectangle region, Graphics2D graphics, Color color) {
        Color original = graphics.getColor();
        graphics.setColor(color);
        graphics.setStroke(new BasicStroke(1, CAP_BUTT, JOIN_MITER, 5, new float[]{5}, 0.0f));
        graphics.drawLine(region.x, region.y, region.width, region.height);
        graphics.drawLine(region.width, region.y, region.x, region.height);
        graphics.drawLine(region.x, region.height / 2, region.width, region.height / 2);
        graphics.drawLine(region.x + region.width / 2, region.y, region.x + region.width / 2, region.height);
        graphics.setColor(original);
    }

    public static void drawOutlineOfRegion(Rectangle region, Graphics2D graphics) {
        graphics.drawRect(region.x, region.y, region.width, region.height);
    }

    public static void drawOutlineOfRegion(Rectangle region, Graphics2D graphics, Color color) {
        Color original = graphics.getColor();
        graphics.setColor(color);
        graphics.setStroke(new BasicStroke(1, CAP_BUTT, JOIN_MITER, 5, new float[]{5}, 0.0f));
        graphics.drawRect(region.x, region.y, region.width, region.height);
        graphics.setColor(original);
    }

    public static class Percentage extends AbstractValueType<Double> {

        public static final Percentage TwentyPercent = percentage(20.0);
        public static final Percentage FiftyPercent = percentage(50.0);
        public static final Percentage EightyPercent = percentage(80.0);

        public static Percentage percentage(Double value) {
            return new Percentage(value);
        }

        public static Percentage percentage(Integer value) {
            return new Percentage(value.doubleValue());
        }

        private Percentage(Double value) {
            super(value);
        }

        private Double of(Integer number) {
            return number * (value / 100);
        }
    }
}
