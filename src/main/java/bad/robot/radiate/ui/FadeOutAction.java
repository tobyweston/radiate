package bad.robot.radiate.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import static java.lang.String.*;

class FadeOutAction implements ActionListener {

    private final PropertyChangeSupport listeners = new PropertyChangeSupport(this);

    private int fadeLimit = 10;
    private int fadeCount = fadeLimit;

    public FadeOutAction(PropertyChangeListener... listeners) {
        for (PropertyChangeListener listener : listeners)
            this.listeners.addPropertyChangeListener(listener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (--fadeCount >= 0)
            listeners.firePropertyChange("fadeOut", 0, (float) fadeCount / (float) fadeLimit);
    }

    @Override
    public String toString() {
        return format("%d", fadeCount);
    }
}
