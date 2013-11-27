package bad.robot.radiate;

import com.googlecode.totallylazy.Callable2;
import com.googlecode.totallylazy.Sequence;

import static bad.robot.radiate.Activity.*;
import static bad.robot.radiate.Activity.Error;
import static com.googlecode.totallylazy.Sequences.sequence;

class ActivityAggregator {

    private final Iterable<Activity> activity;

    private ActivityAggregator(Iterable<Activity> activity) {
        this.activity = activity;
    }

    public static ActivityAggregator aggregated(Iterable<Activity> statuses) {
        return new ActivityAggregator(statuses);
    }

    public Activity getActivity() {
        Sequence<Activity> activities = sequence(this.activity);
        if (activities.isEmpty())
            return Idle;
        return activities.tail().fold(activities.head(), new Callable2<Activity, Activity, Activity>() {
            @Override
            public Activity call(Activity first, Activity second) {
                if (first == Error || second == Error)
                    return Error;
                if (first == Busy || second == Busy)
                    return Busy;
                if (first == Progressing || second == Progressing)
                    return Progressing;
                return Idle;
            }
        });
    }

}
