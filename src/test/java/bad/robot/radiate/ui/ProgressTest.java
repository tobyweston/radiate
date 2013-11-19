package bad.robot.radiate.ui;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ProgressTest {

    @Test
    public void stringRepresentation() throws Exception {
        assertThat(new Progress(20, 100).toString(), is("20%"));
        assertThat(new Progress(40, 200).toString(), is("20%"));
        assertThat(new Progress(40, 120).toString(), is("33%"));
    }
}
