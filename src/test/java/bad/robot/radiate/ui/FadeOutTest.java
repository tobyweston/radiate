package bad.robot.radiate.ui;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class FadeOutTest {

    private final PropertyChangeListenerStub listener = new PropertyChangeListenerStub();
    private final FadeOut fade = new FadeOut();

    private static final int fadeCount = 10;

    @Test
    public void setsTheAlphaTransparency() {
        for (int i = 0; i < fadeCount; i++)
            fade.fireEvent(listener);
        assertThat(listener.contains("bad.robot.radiate.ui.AlphaTransparencyChangeEvent[propertyName=fade; oldValue=1.0; newValue=0.9; propagationId=null; source=0.90]"), is(true));
        assertThat(listener.contains("bad.robot.radiate.ui.AlphaTransparencyChangeEvent[propertyName=fade; oldValue=0.9; newValue=0.8; propagationId=null; source=0.80]"), is(true));
        assertThat(listener.contains("bad.robot.radiate.ui.AlphaTransparencyChangeEvent[propertyName=fade; oldValue=0.8; newValue=0.7; propagationId=null; source=0.70]"), is(true));
        assertThat(listener.contains("bad.robot.radiate.ui.AlphaTransparencyChangeEvent[propertyName=fade; oldValue=0.7; newValue=0.6; propagationId=null; source=0.60]"), is(true));
        assertThat(listener.contains("bad.robot.radiate.ui.AlphaTransparencyChangeEvent[propertyName=fade; oldValue=0.6; newValue=0.5; propagationId=null; source=0.50]"), is(true));
        assertThat(listener.contains("bad.robot.radiate.ui.AlphaTransparencyChangeEvent[propertyName=fade; oldValue=0.5; newValue=0.4; propagationId=null; source=0.40]"), is(true));
        assertThat(listener.contains("bad.robot.radiate.ui.AlphaTransparencyChangeEvent[propertyName=fade; oldValue=0.4; newValue=0.3; propagationId=null; source=0.30]"), is(true));
        assertThat(listener.contains("bad.robot.radiate.ui.AlphaTransparencyChangeEvent[propertyName=fade; oldValue=0.3; newValue=0.2; propagationId=null; source=0.20]"), is(true));
        assertThat(listener.contains("bad.robot.radiate.ui.AlphaTransparencyChangeEvent[propertyName=fade; oldValue=0.2; newValue=0.1; propagationId=null; source=0.10]"), is(true));
        assertThat(listener.contains("bad.robot.radiate.ui.AlphaTransparencyChangeEvent[propertyName=fade; oldValue=0.1; newValue=0.0; propagationId=null; source=0.00]"), is(true));
    }

    @Test
    public void eventDoesNotFire() {
        for (int i = 0; i < (fadeCount + 1); i++)
            fade.fireEvent(listener);
        assertThat(listener.size(), is(fadeCount    ));
    }

    @Test
    public void eventIsDone() {
        for (int i = 0; i < (fadeCount -1); i++)
            fade.fireEvent(listener);
        assertThat(fade.done(), is(false));
        fade.fireEvent(listener);
        assertThat(fade.done(), is(true));
    }

}
