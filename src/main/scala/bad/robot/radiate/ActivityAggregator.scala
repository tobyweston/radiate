package bad.robot.radiate

object ActivityAggregator {
  def aggregated(statuses: List[Activity]) = new ActivityAggregator(statuses)
}

class ActivityAggregator(activities: List[Activity]) {

  def getActivity: Activity = {
    if (activities.isEmpty) Idle
    else activities.reduce[Activity] {
        case (first, second) if first == Error || second == Error => Error
        case (first, second) if first == Busy || second == Busy => Busy
        case (first, second) if first == Progressing || second == Progressing => Progressing
        case _ => Idle
      }
  }
}