package bad.robot.radiate

/**
 * <ul><li>Ok - A positive build state; for example, all tests passing.</li></ul>
 * <ul><li>Status#Broken - A negative build state; for example, tests breaking.</li></ul>
 * <ul><li>Status#Unknown - Unknown build state.
 */
sealed trait StatusS
object StatusS {
  def fromJava(status: Status): StatusS = status match {
    case Status.Ok => Ok
    case Status.Broken => Broken
    case Status.Unknown => Unknown
    case _ => throw new IllegalArgumentException("unsupported status: " + status)
  }
}
case object Ok extends StatusS
case object Broken extends StatusS
case object Unknown extends StatusS
