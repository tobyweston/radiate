package bad.robot.radiate.ui;

import java.beans.PropertyChangeListener;

@Deprecated
interface Fade {
    void fireEvent(PropertyChangeListener[] listeners);
    boolean done();
}
