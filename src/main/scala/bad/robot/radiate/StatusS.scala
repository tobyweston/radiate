package bad.robot.radiate

sealed trait StatusS

/** Ok - A positive build state; for example, all tests passing */
case object Ok extends StatusS

/** A negative build state; for example, tests failing. */
case object Broken extends StatusS

/** Unknown build state. */
case object Unknown extends StatusS
