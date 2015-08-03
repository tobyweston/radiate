package bad.robot.radiate.teamcity;

import junit.framework.TestCase;
import org.junit.Test;

import static com.googlecode.totallylazy.matchers.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Deprecated
public class NoPasswordTest {

    @Test
    public void basicEquality() {
        assertThat(new NoPassword(), is(new NoPassword()));
        assertThat(new NoPassword().equals(null), is(false));
        assertThat(new NoPassword() == new NoPassword(), is(false));
    }

}
