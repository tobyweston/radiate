package bad.robot.radiate.monitor

import bad.robot.radiate.ParseError
import bad.robot.radiate.teamcity.{AllProjectsAsSingleTask, AllProjectsOneTaskPerProject}
import bad.robot.radiate.Error

import scalaz.\/

trait MonitoringTasksFactory extends Observable {
  def create: Error \/ List[MonitoringTask]
}

object MonitoringTasksFactory {
  /** Default mode */
  def singleAggregate = new AllProjectsAsSingleTask

  /** Chessboard mode */
  def multipleProjects = new AllProjectsOneTaskPerProject

  def multipleBuildsDemo = new MultipleBuildsDemo

  def demo = new Demo

  def erroring = new ThreadSafeObservable with MonitoringTasksFactory {
    import scalaz.syntax.either._
    def create = ParseError("An unrecoverable error occurred").left
  }

}
