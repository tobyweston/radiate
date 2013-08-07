package bad.robot.radiate.teamcity;

import bad.robot.radiate.Status;
import org.junit.Test;

import java.util.Collections;

import static bad.robot.radiate.Status.*;
import static bad.robot.radiate.teamcity.StatusAggregator.aggregated;
import static com.googlecode.totallylazy.matchers.Matchers.is;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertThat;

public class StatusAggregatorTest {

    @Test
    public void shouldBeOk() {
        assertThat(aggregated(asList(Ok, Ok, Ok)).getStatus(), is(Ok));
    }

    @Test
    public void shouldBeBroken() {
        assertThat(aggregated(asList(Ok, Broken, Unknown)).getStatus(), is(Broken));
    }

    @Test
    public void shouldBeUnknown() {
        assertThat(aggregated(asList(Ok, Ok, Unknown)).getStatus(), is(Unknown));
    }

    @Test
    public void nothingToAggregate() {
        assertThat(aggregated(Collections.<Status>emptyList()).getStatus(), is(Unknown));
    }


}
