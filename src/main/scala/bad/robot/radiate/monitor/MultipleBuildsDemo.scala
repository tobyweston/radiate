package bad.robot.radiate.monitor

class MultipleBuildsDemo extends ThreadSafeObservable with MonitoringTasksFactory {
  def create: List[MonitoringTask] = {
    Stream.continually(new RandomStatus).take(4 * 4).toList
  }
}