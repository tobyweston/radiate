package bad.robot.radiate

import org.specs2.execute.{Success, Result, AsResult}

package object specs2 {

  implicit def monitoringTasksAsResult: AsResult[MonitoringTasksS] = new AsResult[MonitoringTasksS] {
    def asResult(task: => MonitoringTasksS): Result = Success(task.toString())
  }

  // not sure why this seems to not run the test
  @deprecated implicit def unitAsResult: AsResult[Unit] = new AsResult[Unit] {
    def asResult(nothing: => Unit): Result = Success()
  }
}
