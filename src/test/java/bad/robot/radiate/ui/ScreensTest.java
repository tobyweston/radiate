package bad.robot.radiate.ui;

import org.junit.Test;

import javax.swing.*;
import java.util.function.Supplier;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

public class ScreensTest {

    @Test (expected = IllegalArgumentException.class)
    public void handlesNoScreens() {
        new Screens();
    }

    @Test
    public void singleScreens() {
        Supplier<ScreenModeFactory> a = () -> bounds -> new A();
        Screens screens = new Screens(a);
        assertThat(screens.next().create(null), instanceOf(A.class));
        assertThat(screens.next().create(null), instanceOf(A.class));
    }

    @Test
    public void cyclesScreenModesSuppliedOnConstructor() {
        Supplier<ScreenModeFactory> a = () -> bounds -> new A();
        Supplier<ScreenModeFactory> b = () -> bounds -> new B();
        Screens screens = new Screens(a, b);
        assertThat(screens.next().create(null), instanceOf(A.class));
        assertThat(screens.next().create(null), instanceOf(B.class));
        assertThat(screens.next().create(null), instanceOf(A.class));
    }

    private static class A implements ScreenMode {
        public void accept(JFrame frame) { }
    }

    private static class B implements ScreenMode {
        public void accept(JFrame frame) { }
    }
}