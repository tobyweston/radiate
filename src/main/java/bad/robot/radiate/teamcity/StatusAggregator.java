package bad.robot.radiate.teamcity;

import bad.robot.radiate.Monitorable;
import bad.robot.radiate.Status;
import com.googlecode.totallylazy.Callable2;
import com.googlecode.totallylazy.Sequence;

import static bad.robot.radiate.Status.*;
import static com.googlecode.totallylazy.Sequences.sequence;

public class StatusAggregator implements Monitorable {

    private final Iterable<Status> statuses;

    private StatusAggregator(Iterable<Status> statuses) {
        this.statuses = statuses;
    }

    public static StatusAggregator aggregated(Iterable<Status> statuses) {
        return new StatusAggregator(statuses);
    }

    @Override
    public Status getStatus() {
        Sequence<Status> statuses = sequence(this.statuses);
        if (statuses.isEmpty())
            return Unknown;
        return statuses.fold(Ok, new Callable2<Status, Status, Status>() {
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
