package bad.robot.radiate

import bad.robot.radiate.teamcity.Any
import org.specs2.mutable.Specification

class AggregatorSTest extends Specification {

  "aggregate progress" >> {
    val aggregate = new AggregatorS(List(
      Any.runningBuildPercentageCompleteAt(20),
      Any.build,
      Any.runningBuildPercentageCompleteAt(2),
      Any.runningBuildPercentageCompleteAt(3)))
    aggregate.progress.toString must_== "8%"
  }

  "aggregate number of builds" >> {
    val aggregate = new AggregatorS(List(
      Any.runningBuildPercentageCompleteAt(20),
      Any.build,
      Any.runningBuildPercentageCompleteAt(2),
      Any.runningBuildPercentageCompleteAt(3)))
    aggregate.progress.numberOfBuilds must_== 3
  }

  "null object" >> {
    val aggregate = new AggregatorS(List(Any.build, Any.runningBuildPercentageCompleteAt(2)))
    aggregate.progress.numberOfBuilds must_== 1
  }

  "no progress" >> {
    val aggregate = new AggregatorS(List(Any.build, Any.build, Any.build))
    aggregate.progress.numberOfBuilds must_== 0
    aggregate.progress.toString must_== "0%"
  }
}
