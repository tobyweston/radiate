package bad.robot.radiate;

import org.junit.Test;

import java.util.function.Supplier;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CircularArrayBufferTest {

    @Test (expected = IllegalArgumentException.class)
    public void handlesNoScreens() {
        new CircularArrayBuffer();
    }

    @Test
    public void singleScreens() {
        CircularArrayBuffer screens = new CircularArrayBuffer(() -> "A");
        assertThat(screens.next(), is("A"));
        assertThat(screens.next(), is("A"));
    }

    @Test
    public void cyclesScreenModesSuppliedOnConstructor() {
        Supplier<String> a = () -> "A";
        Supplier<String> b = () -> "B";
        CircularArrayBuffer screens = new CircularArrayBuffer(a, b);
        assertThat(screens.next(), is("A"));
        assertThat(screens.next(), is("B"));
        assertThat(screens.next(), is("A"));
    }

}