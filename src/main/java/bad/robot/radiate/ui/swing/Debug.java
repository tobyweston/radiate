package bad.robot.radiate.ui.swing;

import java.awt.*;

import static java.awt.BasicStroke.CAP_BUTT;
import static java.awt.BasicStroke.JOIN_MITER;
import static java.awt.Color.darkGray;

@Deprecated
public class Debug {

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

}
