package bad.robot.radiate

import bad.robot.radiate.{Status => LegacyStatus}

sealed trait StatusS

object StatusS {
  def fromJava(status: LegacyStatus): StatusS = status match {
    case LegacyStatus.Ok => Ok
    case LegacyStatus.Broken => Broken
    case LegacyStatus.Unknown => Unknown
    case _ => throw new IllegalArgumentException("unsupported status: " + status)
  }
}
/** Ok - A positive build state; for example, all tests passing */
case object Ok extends StatusS

/** A negative build state; for example, tests failing. */
case object Broken extends StatusS

/** Unknown build state. */
case object Unknown extends StatusS
