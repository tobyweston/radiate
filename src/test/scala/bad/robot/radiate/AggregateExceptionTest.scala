package bad.robot.radiate

import java.io.IOException

import org.specs2.mutable.Specification

class AggregateExceptionTest extends Specification {

  "Aggregates messages" >> {
    new AggregateException(List(
      new Exception("Something bad happened"),
      new IOException("An IO problem occurred")
    )).toString must_== """bad.robot.radiate.AggregateException: One or more exception occurred, see below:
                          |1. java.lang.Exception: Something bad happened
                          |2. java.io.IOException: An IO problem occurred
                          |""".stripMargin
  }
}
