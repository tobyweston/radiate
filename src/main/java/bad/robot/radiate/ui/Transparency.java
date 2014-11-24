package bad.robot.radiate.ui;

import bad.robot.http.configuration.AbstractValueType;

import java.awt.*;

import static java.awt.AlphaComposite.SRC_OVER;


public class Transparency extends AbstractValueType<Float> {

    public static Transparency Transparent = new Transparency(0.0f);
    public static Transparency TwentyPercent = new Transparency(0.20f);
    public static Transparency SeventyFivePercent = new Transparency(0.75f);
    public static Transparency Opaque = new Transparency(1.0f);

    public Transparency(float percentage) {
        super(percentage);
    }

    public Transparency increase(float percentage) {
        return new Transparency(value + percentage);
    }

    /**
     * Create a {@link java.awt.AlphaComposite} with transparency set on top of the existing composite. Set the transparency
     * relative to the current transparency (ie, 50% of the already 50% transparency).
     */
    public AlphaComposite createAlphaComposite(AlphaComposite current) {
        return AlphaComposite.getInstance(SRC_OVER, current.getAlpha() * value);
    }

    public AlphaComposite createAlphaComposite() {
        return AlphaComposite.getInstance(SRC_OVER, value);
    }

}

