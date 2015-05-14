package bad.robot.radiate;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Deprecated
public class NullProgressTest {

    @Test
    public void basicEquality() {
        assertThat(new NullProgress().equals(new NullProgress()), is(true));
    }

    @Test
    public void showTheNumberOfThingsProgressIsOver() {
        assertThat(new NullProgress().numberOfBuilds(), is(0));
    }

}
