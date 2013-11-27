package bad.robot.radiate.teamcity;

import org.junit.Test;

import static bad.robot.radiate.teamcity.Build.Functions.aggregatedProgress;
import static com.googlecode.totallylazy.Sequences.sequence;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BuildTest {

    @Test
    public void aggregateProgress() throws Exception {
        Progress progress = aggregatedProgress(sequence(
                Any.runningBuildPercentageCompleteAt(20),
                Any.build(),
                Any.runningBuildPercentageCompleteAt(2),
                Any.runningBuildPercentageCompleteAt(3)));
        assertThat(progress.toString(), is("8%"));
    }
}
