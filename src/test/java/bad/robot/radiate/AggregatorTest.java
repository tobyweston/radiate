package bad.robot.radiate;

import bad.robot.radiate.teamcity.Any;
import com.googlecode.totallylazy.Sequences;
import org.junit.Test;

import static bad.robot.radiate.Aggregator.aggregate;
import static com.googlecode.totallylazy.Sequences.sequence;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class AggregatorTest {

    @Test
    public void aggregateProgress() {
        Aggregator aggregate = aggregate(Sequences.sequence(
            Any.runningBuildPercentageCompleteAt(20),
            Any.build(),
            Any.runningBuildPercentageCompleteAt(2),
            Any.runningBuildPercentageCompleteAt(3)));
        assertThat(aggregate.progress().toString(), is("8%"));
    }

    @Test
    public void aggregateNumberBuilds() {
        Aggregator aggregate = aggregate(sequence(
                Any.runningBuildPercentageCompleteAt(20),
                Any.build(),
                Any.runningBuildPercentageCompleteAt(2),
                Any.runningBuildPercentageCompleteAt(3)));
        Progress progress = aggregate.progress();
        assertThat(progress.numberOfBuilds(), is(3));
    }

    @Test
    public void nullObject() {
        Aggregator aggregate = aggregate(sequence(Any.build(), Any.runningBuildPercentageCompleteAt(2)));
        assertThat(aggregate.progress().numberOfBuilds(), is(1));
    }

    @Test
    public void noProgress() {
        Aggregator aggregate = aggregate(sequence(Any.build(), Any.build(), Any.build()));
        assertThat(aggregate.progress().numberOfBuilds(), is(0));
        assertThat(aggregate.progress().toString(), is("0%"));
    }
}