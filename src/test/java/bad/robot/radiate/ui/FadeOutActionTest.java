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

    @Test
    public void setsTheAlphaTransparency() {
        FadeOutAction action = new FadeOutAction(listener);
        for (int i = 0; i < 10; i++)
            action.actionPerformed(new ActionEvent(this, 1, "whatever"));
        assertThat(listener.contains("java.beans.PropertyChangeEvent[propertyName=fadeOut; oldValue=0; newValue=0.9; propagationId=null; source=9]"), is(true));
        assertThat(listener.contains("java.beans.PropertyChangeEvent[propertyName=fadeOut; oldValue=0; newValue=0.8; propagationId=null; source=8]"), is(true));
        assertThat(listener.contains("java.beans.PropertyChangeEvent[propertyName=fadeOut; oldValue=0; newValue=0.7; propagationId=null; source=7]"), is(true));
        assertThat(listener.contains("java.beans.PropertyChangeEvent[propertyName=fadeOut; oldValue=0; newValue=0.6; propagationId=null; source=6]"), is(true));
        assertThat(listener.contains("java.beans.PropertyChangeEvent[propertyName=fadeOut; oldValue=0; newValue=0.5; propagationId=null; source=5]"), is(true));
        assertThat(listener.contains("java.beans.PropertyChangeEvent[propertyName=fadeOut; oldValue=0; newValue=0.4; propagationId=null; source=4]"), is(true));
        assertThat(listener.contains("java.beans.PropertyChangeEvent[propertyName=fadeOut; oldValue=0; newValue=0.3; propagationId=null; source=3]"), is(true));
        assertThat(listener.contains("java.beans.PropertyChangeEvent[propertyName=fadeOut; oldValue=0; newValue=0.2; propagationId=null; source=2]"), is(true));
        assertThat(listener.contains("java.beans.PropertyChangeEvent[propertyName=fadeOut; oldValue=0; newValue=0.1; propagationId=null; source=1]"), is(true));
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
    }
}
