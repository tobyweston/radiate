package bad.robot.radiate

sealed trait Status

/** Ok - A positive build state; for example, all tests passing */
case object Ok extends Status

/** A negative build state; for example, tests failing. */
case object Broken extends Status

/** Unknown build state. */
case object Unknown extends Status
