package bad.robot.radiate

import bad.robot.radiate.AggregateException._

object AggregateException {
  def summary(errors: List[Exception]) = {
    errors.zipWithIndex.map {
      case (exception, index) => s"${index + 1}. $exception"
    }.mkString("One or more exception occurred, see below:\n", "\n", "\n")
  }
}

class AggregateException(val errors: List[Exception]) extends Exception(summary(errors)) {

}
