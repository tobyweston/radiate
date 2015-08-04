package bad.robot.radiate

import bad.robot.radiate.teamcity.Build

object Aggregator {
  def aggregate(statuses: List[Build]): Aggregator = {
    new Aggregator(statuses)
  }
}

class Aggregator(builds: List[Build]) {

  def activity: Activity = {
    val activities = builds.map(_.activity)
    ActivityAggregator.aggregated(activities).getActivity
  }

  def status: Status = {
    val statuses = builds.map(_.status)
    StatusAggregator.aggregated(statuses).getStatus
  }

  def progress: Progress = {
    builds.tail.foldLeft(builds.head.progress) { (progress, build) => {
      progress.add(build.progress)
    }}
  }
}