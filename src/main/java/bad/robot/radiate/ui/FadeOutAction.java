package bad.robot.radiate.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import static java.lang.String.*;

class FadeOutAction implements ActionListener {

    private final PropertyChangeSupport listeners = new PropertyChangeSupport(this);

    private float fadeLimit = 10f;
    private float fadeCount = fadeLimit;

    public FadeOutAction(PropertyChangeListener... listeners) {
        for (PropertyChangeListener listener : listeners)
            this.listeners.addPropertyChangeListener(listener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (--fadeCount >= 0)
            listeners.firePropertyChange("fadeOut", (fadeCount + 1) / fadeLimit, fadeCount / fadeLimit);
    }

    @Override
    public String toString() {
        return format("%.2f", fadeCount / fadeLimit);
    }
}
