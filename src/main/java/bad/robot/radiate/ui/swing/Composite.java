package bad.robot.radiate.ui.swing;

import bad.robot.radiate.ui.Transparency;

import java.awt.*;
import java.util.concurrent.Callable;

public class Composite {

    public static void applyWithComposite(Graphics2D graphics, java.awt.Composite composite, Callable<Void> callable) {
        java.awt.Composite original = graphics.getComposite();
        graphics.setComposite(composite);
        try {
            callable.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            graphics.setComposite(original);
        }
    }

    /**
     * Create an {@link java.awt.AlphaComposite} from the #graphics object and apply the #transparency to it. Use so that the transparency
     * used is relative to the current transparency (ie, 50% of an already 50% transparent context).
     */
    public static AlphaComposite transparent(Graphics2D graphics, Transparency transparency) {
        return transparency.createAlphaComposite((AlphaComposite) graphics.getComposite());
    }

    public static AlphaComposite transparent(Transparency transparency) {
        return transparency.createAlphaComposite();
    }

}
