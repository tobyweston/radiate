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
    public void simulateRunningAndCompleteBuilds() {
        Progress complete = new Progress(100, 100);
        Progress running = new Progress(20, 100);
        assertThat(complete.add(running).toString(), is("60%"));
    }

    @Test
    public void showTheNumberOfThingsProgressIsOver() {
        assertThat(new Progress(0, 200).over(), is(1));
        assertThat(new Progress(0, 100).add(new Progress(50, 300)).over(), is(2));
        assertThat(new Progress(0, 100).add(new Progress(20, 100)).add(new Progress(50, 300)).over(), is(3));
    }

    @Test
    public void ignoreNumberOfThingsWhenNullObjectIsUsed() {
        assertThat(new Progress(0, 0).over(), is(1));
        assertThat(new Progress(0, 0).add(new Progress(0, 0)).over(), is(2));
        assertThat(new Progress(0, 0).add(new NullProgress()).over(), is(1));
        assertThat(new Progress(0, 0).add(new NullProgress()).add(new NullProgress()).over(), is(1));
    }

    @Test
    public void lessThan() {
        Progress sixtyPercent = new Progress(60, 100);
        Progress fiftyPercent = new Progress(100, 200);
        assertThat(fiftyPercent.lessThan(sixtyPercent), is(true));
    }

    @Test
    public void greaterThan() {
        Progress sixtyPercent = new Progress(60, 100);
        Progress fiftyPercent = new Progress(100, 200);
        assertThat(sixtyPercent.greaterThan(fiftyPercent), is(true));
    }

}
