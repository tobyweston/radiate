package bad.robot.radiate

import bad.robot.radiate.teamcity.BuildS

object AggregatorS {
  def aggregate(statuses: List[BuildS]): AggregatorS = {
    new AggregatorS(statuses)
  }
}

class AggregatorS(builds: List[BuildS]) {

  def activity: ActivityS = {
    val activities = builds.map(_.activity)
    ActivityAggregatorS.aggregated(activities).getActivity
  }

  def status: StatusS = {
    val statuses = builds.map(_.status)
    StatusAggregatorS.aggregated(statuses).getStatus
  }

  def progress: ProgressS = {
    builds.tail.foldLeft(builds.head.progress) { (progress, build) => {
      progress.add(build.progress)
    }}
  }
}