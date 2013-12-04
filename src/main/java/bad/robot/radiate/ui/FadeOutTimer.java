package bad.robot.radiate.ui;

import javax.swing.*;
import javax.swing.plaf.LayerUI;
import java.awt.*;

import static java.awt.AlphaComposite.SRC_OVER;
import static java.awt.AlphaComposite.getInstance;

class FadeOutTimer extends Timer {

    public FadeOutTimer(LayerUI<?> layer, int delay) {
        super(delay, new FadeOutAction(layer.getPropertyChangeListeners()));
    }

    public void fadeOut(JLayer layer, float fade) {
        if (fade != 1.0f) {
            System.out.print(".");
            Graphics2D graphics = (Graphics2D) layer.getGraphics();
            Composite original = graphics.getComposite();
            setCompositeToTransparent(graphics, fade);
            graphics.fillRect(0, 0, layer.getWidth() / 2, layer.getHeight() / 2);
            graphics.setComposite(original);
        }
    }

    private void setCompositeToTransparent(Graphics2D graphics, float alpha) {
        if (alpha >= 0.0f && alpha <= 1.0f)
            graphics.setComposite(getInstance(AlphaComposite.SRC_OVER, alpha));
    }
}
