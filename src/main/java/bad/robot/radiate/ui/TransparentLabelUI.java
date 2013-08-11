package bad.robot.radiate.ui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicLabelUI;
import java.awt.*;

import static java.awt.AlphaComposite.SRC_OVER;
import static java.awt.AlphaComposite.getInstance;
import static java.awt.Font.BOLD;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;

public class TransparentLabelUI extends BasicLabelUI {

    private boolean isDarkColorScheme = true;

    @Override
    protected void installDefaults(JLabel label) {
        super.installDefaults(label);
        initHudComponent(label, isDarkColorScheme);
    }

    public static void initHudComponent(JComponent component, boolean isDarkColorScheme) {
        Font font = UIManager.getFont("Button.font").deriveFont(BOLD, 11.0f);
        component.setFont(font);
        if (isDarkColorScheme)
            component.setForeground(Color.white);
        else
            component.setForeground(Color.black);
        component.setOpaque(false);
    }

    @Override
    public void paint(Graphics graphics, JComponent component) {
        updateGraphicsToPaintDisabledControlIfNecessary((Graphics2D) graphics, component);
        ((Graphics2D) graphics).setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
        super.paint(graphics, component);
    }

    public static void updateGraphicsToPaintDisabledControlIfNecessary(Graphics2D graphics, Component component) {
        if (!component.isEnabled())
            graphics.setComposite(getInstance(SRC_OVER, 0.5f));
    }


    @Override
    protected void paintDisabledText(JLabel label, Graphics graphics, String string, int textX, int textY) {
        super.paintEnabledText(label, graphics, string, textX, textY);
    }
}
