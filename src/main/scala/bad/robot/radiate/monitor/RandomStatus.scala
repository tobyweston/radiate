package bad.robot.radiate.monitor

import java.security.SecureRandom

import bad.robot.radiate.Activity.{Error, _}
import bad.robot.radiate.Status._
import bad.robot.radiate.monitor.RandomStatusS.{randomActivity, _}
import bad.robot.radiate._

object RandomStatusS {

  private val random = new SecureRandom
  private val statuses = Array[Status](Ok, Ok, Ok, Ok, Ok, Ok, Ok, Ok, Ok, Broken, Unknown)
  private val activities = Array[Activity](Busy, Error, Idle, Progressing)

  def randomProgress: Progress = {
    new Progress(random.nextInt(100) + 1, 100)
  }

  def randomStatus: Status = {
    statuses(random.nextInt(statuses.length))
  }

  private def randomActivity: Activity = {
    activities(random.nextInt(activities.length))
  }
}

class RandomStatusS extends ThreadSafeObservableS with MonitoringTaskS {
  def run {
    val activity = randomActivity
    val status = RandomStatus.randomStatus
    notifyObservers(activity, if (activity == Progressing) randomProgress else new NullProgress)
    notifyObservers(status)
    if (status == Broken)
      notifyObservers(new RuntimeException("Example problem"))
  }

  override def toString: String = "randomly passing build monitoring"
}