package bad.robot.radiate.ui;

import java.beans.PropertyChangeEvent;

import static java.lang.String.format;

class AlphaTransparencyChangeEvent extends PropertyChangeEvent {

    public AlphaTransparencyChangeEvent(Object action, float oldValue, float newValue) {
        super(action, "fade", oldValue, newValue);
    }

}
