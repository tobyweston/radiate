package bad.robot.radiate

trait Monitorable {
  def status: Status
  def activity: Activity
  def progress: Progress
}
