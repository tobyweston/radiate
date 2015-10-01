package bad.robot.radiate

import org.specs2.mutable.Specification

class AggregateErrorTest extends Specification {

  "Aggregates messages" >> {
    new AggregateError(List(
      new ConfigurationError("Something bad happened"),
      new ParseError("A parse problem occurred")
    )).message must_== """One or more exception occurred, see below:
                          |1. ConfigurationError: Something bad happened
                          |2. ParseError: A parse problem occurred
                          |""".stripMargin
  }
}
