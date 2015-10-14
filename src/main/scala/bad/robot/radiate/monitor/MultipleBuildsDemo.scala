package bad.robot.radiate.monitor

import bad.robot.radiate.Error
import scalaz.\/
import scalaz.syntax.either._

class MultipleBuildsDemo extends ThreadSafeObservable with MonitoringTasksFactory {
  def create: Error \/ List[MonitoringTask] = {
    Stream.continually(new RandomStatus).take(4 * 4).toList.right
  }
}