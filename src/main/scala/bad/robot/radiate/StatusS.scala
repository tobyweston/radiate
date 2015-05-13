package bad.robot.radiate

sealed trait StatusS

object StatusS {
  def fromJava(status: Status): StatusS = status match {
    case Status.Ok => Ok
    case Status.Broken => Broken
    case Status.Unknown => Unknown
    case _ => throw new IllegalArgumentException("unsupported status: " + status)
  }
}
/** Ok - A positive build state; for example, all tests passing */
case object Ok extends StatusS

/** A negative build state; for example, tests failing. */
case object Broken extends StatusS

/** Unknown build state. */
case object Unknown extends StatusS
