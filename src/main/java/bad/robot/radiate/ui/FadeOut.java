package bad.robot.radiate.ui;

import java.beans.PropertyChangeListener;

import static java.lang.String.format;

class FadeOut implements Fade {

    protected float limit = 10f;
    protected float count = limit;

    @Override
    public void fireEvent(PropertyChangeListener... listeners) {
        if (--count >= 0) {
            for (PropertyChangeListener listener : listeners) {
                listener.propertyChange(new AlphaTransparencyChangeEvent(this, (count + 1) / limit, count / limit));
            }
        }
    }

    @Override
    public boolean done() {
        return count == 0;
    }

    @Override
    public String toString() {
        return format("%.2f", count / limit);
    }
}
