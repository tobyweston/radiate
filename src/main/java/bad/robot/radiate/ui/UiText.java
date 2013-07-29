package bad.robot.radiate.ui;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import static java.awt.Color.GRAY;
import static java.awt.Color.LIGHT_GRAY;
import static java.awt.Font.BOLD;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;

class UiText {

    public static Rectangle createTextRegion(int x, int y, int width, int height) {
        int shrinkPercentage = 10;
        Rectangle rectangle = new Rectangle(x, y, width, height);
        rectangle.grow(-(width / shrinkPercentage), -(height / shrinkPercentage));
        return rectangle;
    }

    public static void drawText(Graphics g, Rectangle region, String text) {
        Graphics2D graphics = (Graphics2D) g.create();
        Font font = new Font("Arial", BOLD, 24);
        graphics.setFont(font);
//        AffineTransform reduction = createReduction(region, text, graphics, font);
//        graphics.setFont(font.deriveFont(reduction));
        graphics.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
        graphics.setColor(GRAY);
        graphics.drawString(text, region.x, region.y);
        graphics.setColor(LIGHT_GRAY);
        graphics.drawString(text, region.x - 1, region.y - 1);
        graphics.dispose();
    }

    private static AffineTransform createReduction(Rectangle region, String text, Graphics2D graphics, Font font) {
        FontRenderContext context = graphics.getFontRenderContext();
        Rectangle2D bounds = graphics.getFont().getStringBounds(text, context);

        LineMetrics metrics = font.getLineMetrics(text, context);
        float xScale = (float) (region.width / bounds.getWidth());
        float yScale = (region.height / (metrics.getAscent() + metrics.getDescent()));

        double x = region.x;
        double y = region.y + region.height - (yScale * metrics.getDescent());
        AffineTransform transformation = AffineTransform.getTranslateInstance(x, y);

        if (xScale > yScale)
            transformation.scale(yScale, yScale);
        else
            transformation.scale(xScale, xScale);
        return transformation;
    }
}
