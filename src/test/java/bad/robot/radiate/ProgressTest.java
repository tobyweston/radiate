package bad.robot.radiate;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ProgressTest {

    @Test
    public void stringRepresentation() {
        assertThat(new Progress(20, 100).toString(), is("20%"));
        assertThat(new Progress(30, 200).toString(), is("15%"));
        assertThat(new Progress(40, 200).toString(), is("20%"));
        assertThat(new Progress(40, 120).toString(), is("33%"));
    }

    @Test
    public void adding() {
        Progress progress = new Progress(10, 100).add(new Progress(20, 100));
        assertThat(progress.toString(), is("15%"));
    }

    @Test
    public void simulateRunningAndCompleteBuilds() throws Exception {
        Progress complete = new Progress(100, 100);
        Progress running = new Progress(20, 100);
        assertThat(complete.add(running).toString(), is("60%"));
    }
}
