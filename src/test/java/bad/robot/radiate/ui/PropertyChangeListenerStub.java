package bad.robot.radiate.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

class PropertyChangeListenerStub implements PropertyChangeListener {

    private final List<String> results = new ArrayList<>();

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        results.add(event.toString());
    }

    public boolean contains(String result) {
        return results.contains(result);
    }

    public int size() {
        return results.size();
    }
}
