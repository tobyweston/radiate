package bad.robot.radiate.monitor

import bad.robot.radiate.ThreadSafeObservableS
import bad.robot.radiate.teamcity.{AllProjectsOneTaskPerProject, AllProjectsAsSingleTask}

trait MonitoringTasksFactoryS extends ObservableS {
  def create: List[MonitoringTaskS]
}

object MonitoringTasksFactoryS {
  /** Default mode */
  def singleAggregate = new AllProjectsAsSingleTask

  /** Chessboard mode */
  def multipleProjects = new AllProjectsOneTaskPerProject

  def multipleBuildsDemo = new MultipleBuildsDemoS

  def demo = new Demo

  def erroring = new Error

  class Error extends ThreadSafeObservableS with MonitoringTasksFactoryS {
    def create: List[MonitoringTaskS] = {
      throw new RuntimeException("An unrecoverable error occurred")
    }
  }

}
