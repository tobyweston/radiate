package bad.robot.radiate

object LegacyJavaConverters {

  implicit def toActivity(scala: ActivityS): Activity = {
    scala match {
      case Busy => Activity.Busy
      case Error => Activity.Error
      case Idle => Activity.Idle
      case Progressing => Activity.Progressing
      case _ => throw new UnsupportedOperationException
    }
  }
}
