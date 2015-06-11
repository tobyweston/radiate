package bad.robot.radiate

import org.specs2.execute.{AsResult, Result, Success}

package object specs2 {

  implicit def monitoringTasksAsResult: AsResult[MonitoringTasksS] = new AsResult[MonitoringTasksS] {
    def asResult(function: => MonitoringTasksS): Result = {
      Success(function.toString())
    }
  }

  implicit def unitAsResult: AsResult[Unit] = new AsResult[Unit] {
    def asResult(function: => Unit): Result = {
      function
      Success()
    }
  }
  
  implicit def iterableAsResult[A]: AsResult[Iterable[A]] = new AsResult[Iterable[A]] {
    override def asResult(function: => Iterable[A]): Result = {
      Success(function.mkString)
    }
  }
}
