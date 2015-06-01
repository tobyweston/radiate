package bad.robot.radiate.monitor

import bad.robot.radiate.monitor.ThreadSafeObservableS

class MultipleBuildsDemoS extends ThreadSafeObservableS with MonitoringTasksFactoryS {
  def create: List[MonitoringTaskS] = {
    Stream.continually(new RandomStatusS).take(4 * 4).toList
  }
}