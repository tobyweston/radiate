package bad.robot.radiate.ui;

import java.beans.PropertyChangeListener;

import static java.lang.String.format;

class FadeIn implements Fade {

    protected float limit = 50f;
    protected float count = 0f;

    @Override
    public void fireEvent(PropertyChangeListener... listeners) {
        if (++count <= limit) {
            System.out.print("i");
            for (PropertyChangeListener listener : listeners) {
                listener.propertyChange(new AlphaTransparencyChangeEvent(this, (count - 1) / limit, count / limit));
            }
        }
    }

    @Override
    public boolean done() {
        return count == limit;
    }

    @Override
    public String toString() {
        return format("%.2f", count / limit);
    }
}
