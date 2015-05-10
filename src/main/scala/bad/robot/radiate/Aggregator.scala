package bad.robot.radiate

import bad.robot.radiate.teamcity.Build

object AggregatorS {
  def aggregate(statuses: List[Build]): AggregatorS = {
    new AggregatorS(statuses)
  }
}

class AggregatorS(builds: List[Build]) {

  def activity: ActivityS = {
    val activities = builds.map(_.getActivity).map(ActivityS.fromJava)
    ActivityAggregatorS.aggregated(activities).getActivity
  }

  def status: Status = {
    import scala.collection.JavaConverters._
    val statuses = builds.map(_.getStatus).asJava
    StatusAggregator.aggregated(statuses).getStatus
  }

  def progress: Progress = {
    builds.foldLeft(builds.head.getProgress) { (progress, build) => {
      progress.add(build.getProgress)
    }}
  }
}