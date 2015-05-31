package bad.robot.radiate

object StatusAggregatorS {
  def aggregated(statuses: Iterable[StatusS]) = new StatusAggregatorS(statuses)
}

class StatusAggregatorS(statuses: Iterable[StatusS]) {

  def getStatus: StatusS = {
    statuses.reduce[StatusS] {
      case (first, second) if first == Broken || second == Broken => Broken
      case (first, second) if first == Unknown || second == Unknown => Unknown
      case _ if statuses.isEmpty => Unknown
      case _ => Ok;
    }
  }
}