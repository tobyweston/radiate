package bad.robot.radiate

trait MonitorableS {
  def status: StatusS
  def activity: ActivityS
  def progress: ProgressS
}
