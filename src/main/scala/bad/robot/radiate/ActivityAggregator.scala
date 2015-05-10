package bad.robot.radiate
import bad.robot.radiate.ActivityS._

object ActivityAggregatorS {
  def aggregated(statuses: List[ActivityS]): ActivityAggregatorS = {
    new ActivityAggregatorS(statuses)
  }
}

class ActivityAggregatorS(activities: List[ActivityS]) {

  def getActivity: ActivityS = {
    if (activities.isEmpty)
      Idle
    else
      activities.reduce[ActivityS] {
        case (first, second) if first == Error || second == Error => Error
        case (first, second) if first == Busy || second == Busy => Busy
        case (first, second) if first == Progressing || second == Progressing => Progressing
        case _ => Idle
      }
  }
}