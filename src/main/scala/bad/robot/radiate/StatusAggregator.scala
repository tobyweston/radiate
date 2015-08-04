package bad.robot.radiate

object StatusAggregator {
  def aggregated(statuses: Iterable[Status]) = new StatusAggregator(statuses)
}

class StatusAggregator(statuses: Iterable[Status]) {

  def getStatus: Status = {
    if (statuses.isEmpty) Unknown
    else statuses.reduce[Status] {
      case (first, second) if first == Broken || second == Broken => Broken
      case (first, second) if first == Unknown || second == Unknown => Unknown
      case _ => Ok;
    }
  }
}