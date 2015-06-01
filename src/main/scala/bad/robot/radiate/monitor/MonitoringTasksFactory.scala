package bad.robot.radiate.monitor

import bad.robot.radiate.teamcity.{AllProjectsOneTaskPerProjectS, AllProjectsOneTaskPerProject, AllProjectsAsSingleTaskS}

trait MonitoringTasksFactoryS extends ObservableS {
  def create: List[MonitoringTaskS]
}

object MonitoringTasksFactoryS {
  /** Default mode */
  def singleAggregate = new AllProjectsAsSingleTaskS

  /** Chessboard mode */
  def multipleProjects = new AllProjectsOneTaskPerProjectS

  def multipleBuildsDemo = new MultipleBuildsDemoS

  def demo = new DemoS

  def erroring = new Error

  class Error extends ThreadSafeObservableS with MonitoringTasksFactoryS {
    def create: List[MonitoringTaskS] = {
      throw new RuntimeException("An unrecoverable error occurred")
    }
  }

}
