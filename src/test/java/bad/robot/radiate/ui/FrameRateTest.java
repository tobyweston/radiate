package bad.robot.radiate.ui;

import org.junit.Test;

import static bad.robot.radiate.ui.FrameRate.framesPerSecond;
import static com.googlecode.totallylazy.matchers.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FrameRateTest {

    @Test
    public void framesPerSecondAsMillisecondFrequency() {
        assertThat(framesPerSecond(24).asFrequencyInMillis(), is(41));
        assertThat(framesPerSecond(25).asFrequencyInMillis(), is(40));
        assertThat(framesPerSecond(30).asFrequencyInMillis(), is(33));
    }

    @Test (expected = IllegalArgumentException.class)
    public void tooHigh() {
        framesPerSecond(61);
    }

    @Test (expected = IllegalArgumentException.class)
    public void tooLow() {
        framesPerSecond(0);
    }


}
