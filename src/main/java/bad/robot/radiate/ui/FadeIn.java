package bad.robot.radiate.ui;

import java.beans.PropertyChangeListener;
import java.util.Arrays;

import static java.lang.String.format;
import static java.util.Arrays.*;

@Deprecated
class FadeIn implements Fade {

    private float limit = 50f;
    private float count = 0f;

    @Override
    public void fireEvent(PropertyChangeListener... listeners) {
        if (++count <= limit) {
            stream(listeners).forEach(listener -> listener.propertyChange(new AlphaTransparencyChangeEvent(this, (count - 1) / limit, count / limit)));
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
