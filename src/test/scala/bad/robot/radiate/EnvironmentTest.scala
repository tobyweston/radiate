package bad.robot.radiate

import org.specs2.mutable.Specification
import org.specs2.matcher.SomeMatcher._

class EnvironmentTest extends Specification {

  "Environment var can be retrieved" >> {
    Environment.getEnvironmentVariable("PATH") must beSome
  }

  "Fails to get environment variable" >> {
    Environment.getEnvironmentVariable("banana") must beNone
  }

  "Don't default an environment variable if it exists" >> {
    Environment.getEnvironmentVariable("PATH") must not be empty
  }

  "Default a non-existent environment variable" >> {
    Environment.getEnvironmentVariable("banana", "sandwich") must_== "sandwich"
  }
}
