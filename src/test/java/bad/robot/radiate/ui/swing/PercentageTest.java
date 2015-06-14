package bad.robot.radiate.ui.swing;

import org.junit.Test;

import static bad.robot.radiate.ui.swing.Region.Percentage.OneHundredPercent;
import static bad.robot.radiate.ui.swing.Region.Percentage.percentage;
import static com.googlecode.totallylazy.matchers.NumberMatcher.is;
import static org.junit.Assert.assertThat;

public class PercentageTest {

	@Test
	public void shouldGetPercetnage() {
		assertThat(OneHundredPercent.of(100), is(100));
		assertThat(OneHundredPercent.of(50), is(50));
		assertThat(percentage(68).of(50), is(34));
	}


}