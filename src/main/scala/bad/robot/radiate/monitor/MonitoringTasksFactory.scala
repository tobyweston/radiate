package bad.robot.radiate.monitor

import bad.robot.radiate.teamcity.{AllProjectsAsSingleTask, AllProjectsOneTaskPerProject}

trait MonitoringTasksFactory extends Observable {
  def create: List[MonitoringTask]
}

object MonitoringTasksFactory {
  /** Default mode */
  def singleAggregate = new AllProjectsAsSingleTask

  /** Chessboard mode */
  def multipleProjects = new AllProjectsOneTaskPerProject

  def multipleBuildsDemo = new MultipleBuildsDemo

  def demo = new Demo

  def erroring = new Error

  class Error extends ThreadSafeObservable with MonitoringTasksFactory {
    def create: List[MonitoringTask] = {
      throw new RuntimeException("An unrecoverable error occurred")
    }
  }

}
