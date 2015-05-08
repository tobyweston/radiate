package bad.robot.radiate.ui;

import java.beans.PropertyChangeListener;

interface Fade {
    void fireEvent(PropertyChangeListener[] listeners);
    boolean done();
}
