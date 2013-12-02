package bad.robot.radiate;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class NullProgressTest {

    @Test
    public void basicEquality() {
        assertThat(new NullProgress().equals(new NullProgress()), is(true));
    }

    @Test (expected = UnsupportedOperationException.class)
    public void showTheNumberOfThingsProgressIsOver() {
        Assert.assertThat(new NullProgress().over(), is(1));
    }

}
