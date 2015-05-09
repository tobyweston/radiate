package bad.robot.radiate.monitor;

import java.util.List;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static java.util.stream.Stream.*;

@Deprecated
class MultipleBuildsDemo extends ThreadSafeObservable implements MonitoringTasksFactory {

    @Override
    public List<MonitoringTask> create() {
        return generate(RandomStatus::new).limit(4 * 4).collect(toList());
    }
}
