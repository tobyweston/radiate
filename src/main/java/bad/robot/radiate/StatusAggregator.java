package bad.robot.radiate;

import com.googlecode.totallylazy.Callable2;
import com.googlecode.totallylazy.Sequence;

import static bad.robot.radiate.Status.*;
import static com.googlecode.totallylazy.Sequences.sequence;

public class StatusAggregator {

    private final Iterable<Status> statuses;

    private StatusAggregator(Iterable<Status> statuses) {
        this.statuses = statuses;
    }

    public static StatusAggregator aggregated(Iterable<Status> statuses) {
        return new StatusAggregator(statuses);
    }

    public Status getStatus() {
        Sequence<Status> statuses = sequence(this.statuses);
        if (statuses.isEmpty())
            return Unknown;
        return statuses.tail().fold(statuses.head(), new Callable2<Status, Status, Status>() {
            @Override
            public Status call(Status first, Status second) {
                if (first == Broken || second == Broken)
                    return Broken;
                if (first == Unknown || second == Unknown)
                    return Unknown;
                return Ok;
            }
        });
    }

}
