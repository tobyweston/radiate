package bad.robot.radiate;

import bad.robot.radiate.teamcity.Build;
import com.googlecode.totallylazy.Callable2;
import com.googlecode.totallylazy.Sequence;

import static bad.robot.radiate.ActivityAggregator.aggregated;
import static bad.robot.radiate.teamcity.Build.Functions.toActivity;
import static bad.robot.radiate.teamcity.Build.Functions.toStatus;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Aggregator {

    private final Iterable<Build> builds;

    private Aggregator(Iterable<Build> builds) {
        this.builds = builds;
    }

    public static Aggregator aggregate(Iterable<Build> statuses) {
        return new Aggregator(statuses);
    }

    public Activity activity() {
        Sequence<Activity> activities = sequence(builds).map(toActivity());
        return aggregated(activities).getActivity();
    }

    public Status status() {
        Sequence<Status> statuses = sequence(builds).map(toStatus());
        return StatusAggregator.aggregated(statuses).getStatus();
    }

    public Progress progress() {
        Progress seed = sequence(builds).head().getProgress();
        return sequence(builds).tail().fold(seed, (progress, build) -> progress.add(build.getProgress()));
    }


}
