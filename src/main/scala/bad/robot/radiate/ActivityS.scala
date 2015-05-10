package bad.robot.radiate

/**
 * <ul><li>Busy - Indicate a busy state, this could be used to overlay a 'spinner' to indicate the user should wait. Use when waiting for IO etc. Set to Idle or Error when done.</li></ul>
 * <ul><li>Progressing - Indicate a work is progressing, this could be used to overlay a 'progress bar' to indicate progress. Use when executing a fixed period task. Set to Idle or Error when done.</li></ul>
 * <ul><li>Idle - Indicate an idle, this could be used dismiss any busy or progress indicators. Use having completed work.</li></ul>
 * <ul><li>Error - Indicate an error.
 */
sealed trait ActivityS
object ActivityS {
  def fromJava(activity: Activity): ActivityS = activity match {
    case Activity.Busy => Busy
    case Activity.Progressing => Progressing
    case Activity.Idle => Idle
    case Activity.Error => Error
    case _ => throw new IllegalArgumentException("unsupported activity: " + activity)
  }
}
case object Busy extends ActivityS
case object Progressing extends ActivityS
case object Idle extends ActivityS
case object Error extends ActivityS