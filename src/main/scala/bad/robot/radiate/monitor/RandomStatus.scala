package bad.robot.radiate.monitor

import java.security.SecureRandom

import bad.robot.radiate.monitor.RandomStatusS._
import bad.robot.radiate._

object RandomStatusS {

  private val random = new SecureRandom
  private val statuses = Array(Ok, Ok, Ok, Ok, Ok, Ok, Ok, Ok, Ok, Broken, Unknown)
  private val activities = Array(Busy, Error, Idle, Progressing)

  def randomProgress: ProgressS = {
    new ProgressS(random.nextInt(100) + 1, 100)
  }

  def randomStatus: StatusS = {
    statuses(random.nextInt(statuses.length))
  }

  private def randomActivity: ActivityS = {
    activities(random.nextInt(activities.length))
  }
}

class RandomStatusS extends ThreadSafeObservableS with MonitoringTaskS {
  def run {
    val activity = randomActivity
    val status = randomStatus
    notifyObservers(activity, if (activity == Progressing) randomProgress else new NullProgressS)
    notifyObservers(status)
    if (status == Broken)
      notifyObservers(new RuntimeException("Example problem"))
  }

  override def toString: String = "randomly passing build monitoring"
}