package bad.robot.radiate.ui;

import java.beans.PropertyChangeListener;

interface Fade {
    abstract void fireEvent(PropertyChangeListener[] listeners);
    abstract boolean done();
}
