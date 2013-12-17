package bad.robot.radiate.ui.swing;

import java.awt.*;
import java.util.concurrent.Callable;

import static java.awt.AlphaComposite.SRC_OVER;
import static java.awt.AlphaComposite.getInstance;

public class Composite {

    public static void applyWithComposite(Graphics2D graphics, java.awt.Composite composite, Callable<Void> callable) {
        java.awt.Composite original = graphics.getComposite();
        graphics.setComposite(composite);
        try {
            callable.call();
        } catch (Exception e) {
            // ignore
        } finally {
            graphics.setComposite(original);
        }
    }

    /**
     * Get the {@link java.awt.AlphaComposite} from the #graphics object and apply the #transparency to it. Use so that the transparency
     * used is relative to the current transparency (ie, 50% of an already 50% transparent context).
     */
    public static AlphaComposite getAlphaComposite(Graphics2D graphics, float transparency) {
        AlphaComposite current = (AlphaComposite) graphics.getComposite();
        return getInstance(SRC_OVER, current.getAlpha() * transparency);
    }
}
