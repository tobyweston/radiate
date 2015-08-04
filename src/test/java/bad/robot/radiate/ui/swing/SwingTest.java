package bad.robot.radiate.ui.swing;

import org.junit.Test;

import java.awt.*;

import static bad.robot.radiate.ui.swing.Region.Percentage.percentage;
import static bad.robot.radiate.ui.swing.Region.getReducedRegion;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Deprecated
public class SwingTest {

    @Test
    public void reducedRegionSize() throws Exception {
        Rectangle rectangle = getReducedRegion(new Rectangle(10, 20, 200, 150), percentage(50));
        assertThat(rectangle.x, is(10));
        assertThat(rectangle.y, is(20));
        assertThat(rectangle.width, is(100));
        assertThat(rectangle.height, is(75));
    }

}
