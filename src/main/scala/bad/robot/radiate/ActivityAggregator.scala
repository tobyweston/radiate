package bad.robot.radiate
import bad.robot.radiate.ActivityS._

object ActivityAggregatorS {
  def aggregated(statuses: List[ActivityS.Activity]): ActivityAggregatorS = {
    new ActivityAggregatorS(statuses)
  }
}

class ActivityAggregatorS(activities: List[ActivityS.Activity]) {

  def getActivity: ActivityS.Activity = {
    if (activities.isEmpty)
      Idle
    else
      activities.reduce[ActivityS.Activity] {
        case (first, second) if first == Error || second == Error => Error
        case (first, second) if first == Busy || second == Busy => Busy
        case (first, second) if first == Progressing || second == Progressing => Progressing
        case _ => Idle
      }
  }
}