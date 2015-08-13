package bad.robot.radiate.monitor

import java.security.SecureRandom

import bad.robot.radiate.monitor.RandomStatus._
import bad.robot.radiate._
import bad.robot.radiate._
import activity._

object RandomStatus {

  private val random = new SecureRandom
  private val statuses = Array(Ok, Ok, Ok, Ok, Ok, Ok, Ok, Ok, Ok, Broken, Unknown)
  private val activities = Array(Busy, Error, Idle, Progressing)

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

class RandomStatus extends ThreadSafeObservable with MonitoringTask {
  def run {
    val activity = randomActivity
    val status = randomStatus
    notifyObservers(activity, if (activity == Progressing) randomProgress else new NullProgress)
    notifyObservers(status)
    if (status == Broken)
      notifyObservers(new RuntimeException("Example problem"))
  }

  override def toString: String = "randomly passing build monitoring"
}