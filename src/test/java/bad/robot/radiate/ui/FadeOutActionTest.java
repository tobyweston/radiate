package bad.robot.radiate.ui;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class FadeOutActionTest {

    private final PropertyChangeListenerStub listener = new PropertyChangeListenerStub();
    private final FadeOutAction action = new FadeOutAction(listener);

    @Test
    public void setsTheAlphaTransparency() {
        for (int i = 0; i < 10; i++)
            action.actionPerformed(new ActionEvent(this, 1, "whatever"));
        assertThat(listener.contains("java.beans.PropertyChangeEvent[propertyName=fadeOut; oldValue=1.0; newValue=0.9; propagationId=null; source=0.90]"), is(true));
        assertThat(listener.contains("java.beans.PropertyChangeEvent[propertyName=fadeOut; oldValue=0.9; newValue=0.8; propagationId=null; source=0.80]"), is(true));
        assertThat(listener.contains("java.beans.PropertyChangeEvent[propertyName=fadeOut; oldValue=0.8; newValue=0.7; propagationId=null; source=0.70]"), is(true));
        assertThat(listener.contains("java.beans.PropertyChangeEvent[propertyName=fadeOut; oldValue=0.7; newValue=0.6; propagationId=null; source=0.60]"), is(true));
        assertThat(listener.contains("java.beans.PropertyChangeEvent[propertyName=fadeOut; oldValue=0.6; newValue=0.5; propagationId=null; source=0.50]"), is(true));
        assertThat(listener.contains("java.beans.PropertyChangeEvent[propertyName=fadeOut; oldValue=0.5; newValue=0.4; propagationId=null; source=0.40]"), is(true));
        assertThat(listener.contains("java.beans.PropertyChangeEvent[propertyName=fadeOut; oldValue=0.4; newValue=0.3; propagationId=null; source=0.30]"), is(true));
        assertThat(listener.contains("java.beans.PropertyChangeEvent[propertyName=fadeOut; oldValue=0.3; newValue=0.2; propagationId=null; source=0.20]"), is(true));
        assertThat(listener.contains("java.beans.PropertyChangeEvent[propertyName=fadeOut; oldValue=0.2; newValue=0.1; propagationId=null; source=0.10]"), is(true));
        assertThat(listener.contains("java.beans.PropertyChangeEvent[propertyName=fadeOut; oldValue=0.1; newValue=0.0; propagationId=null; source=0.00]"), is(true));
    }

    @Test
    public void eventDoesNotFire() {
        for (int i = 0; i < 11; i++)
            action.actionPerformed(new ActionEvent(this, 1, "whatever"));
        assertThat(listener.size(), is(10));
    }

    private static class PropertyChangeListenerStub implements PropertyChangeListener {

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
}
