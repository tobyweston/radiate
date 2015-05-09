package bad.robot.radiate

/**
 * <ul><li>Busy - Indicate a busy state, this could be used to overlay a 'spinner' to indicate the user should wait. Use when waiting for IO etc. Set to Idle or Error when done.</li></ul>
 * <ul><li>Progressing - Indicate a work is progressing, this could be used to overlay a 'progress bar' to indicate progress. Use when executing a fixed period task. Set to Idle or Error when done.</li></ul>
 * <ul><li>Idle - Indicate an idle, this could be used dismiss any busy or progress indicators. Use having completed work.</li></ul>
 * <ul><li>Error - Indicate an error.
 */
object ActivityS extends Enumeration {
  type Activity = Value
  val Busy, Progressing, Idle, Error = Value
}
