package bad.robot

import org.scalamock.matchers.ArgThat
import org.specs2.execute.{Success, Results, Result, AsResult}

import scala.reflect.ClassTag

package object radiate {

  def anyTypedOf[T, U: ClassTag]: ArgThat[T] = new ArgThat[T]({
    case x: U => true
    case _ => false
  })

  implicit def monitoringTasksAsResult: AsResult[MonitoringTasksS] = new AsResult[MonitoringTasksS] {
    def asResult(task: => MonitoringTasksS): Result = Success(task.toString)
  }

  implicit def unitAsResult: AsResult[Unit] = new AsResult[Unit] {
    def asResult(nothing: => Unit): Result = Success("Unit")
  }
}
