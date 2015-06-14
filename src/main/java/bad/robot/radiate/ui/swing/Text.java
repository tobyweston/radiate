package bad.robot.radiate.ui.swing;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static java.awt.Font.PLAIN;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import static java.awt.geom.AffineTransform.getScaleInstance;

@Deprecated
public class Text {

    public static void drawTextCenteredToRegion(Rectangle region, Graphics2D graphics, String text) {
        setFontScaledToRegion(region, graphics, text, new Font("Arial", PLAIN, 12));
        Point center = getCenterPointOfTextWithinRegion(region, graphics, graphics.getFont(), text);
        graphics.setColor(Color.white);
        graphics.drawString(text, center.x, region.y + getFontHeight(graphics, text));
    }

    private static float getFontHeight(Graphics2D graphics, String text) {
        FontMetrics metrics = graphics.getFontMetrics(graphics.getFont());
        Rectangle2D textSize = metrics.getStringBounds(text, graphics);
        return (float) (textSize.getHeight()); // + metrics.getAscent());
    }

    public static Point getCenterPointOfTextWithinRegion(Rectangle region, Graphics2D graphics, Font font, String text) {
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
        float scale = determineWhichAxisToScaleOn(xScale, yScale);

        graphics.setFont(font.deriveFont(getScaleInstance(scale, scale)));
        graphics.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
    }

    private static float determineWhichAxisToScaleOn(float xScale, float yScale) {
        if (xScale > yScale)
            return yScale;
        return xScale;
    }
}
