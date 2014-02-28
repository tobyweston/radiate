package bad.robot.radiate.teamcity;

import org.junit.Test;

import static com.googlecode.totallylazy.matchers.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class NoUsernameTest {

    @Test
    public void basicEquality() {
        assertThat(new NoUsername(), is(new NoUsername()));
        assertThat(new NoUsername().equals(null), is(false));
        assertThat(new NoUsername() == new NoUsername(), is(false));
    }
}
