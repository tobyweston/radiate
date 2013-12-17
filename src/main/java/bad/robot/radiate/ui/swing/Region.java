package bad.robot.radiate.ui.swing;

import bad.robot.http.configuration.AbstractValueType;

import java.awt.*;

public class Region {

    public static Rectangle getReducedRegion(Rectangle region, Percentage percentage) {
        return getReducedRegion(region, percentage, percentage);
    }

    public static Rectangle getReducedRegion(Rectangle region, Percentage x, Percentage y) {
        Double width = x.of(region.width);
        Double height = y.of(region.height);
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

    public static class Percentage extends AbstractValueType<Double> {

        public static final Percentage TwentyPercent = percentage(20.0);
        public static final Percentage FiftyPercent = percentage(50.0);
        public static final Percentage EightyPercent = percentage(80.0);
        public static final Percentage NinetyPercent = percentage(90.0);
        public static final Percentage OneHundredPercent = percentage(100.0);

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
