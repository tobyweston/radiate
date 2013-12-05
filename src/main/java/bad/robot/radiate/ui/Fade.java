package bad.robot.radiate.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

interface Fade {
    abstract void fireEvent(PropertyChangeListener[] listeners);
    abstract boolean done();
}
