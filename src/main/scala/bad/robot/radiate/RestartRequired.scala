package bad.robot.radiate

import bad.robot.radiate.monitor.Information

object RestartRequired{
  def restartRequired: RestartRequired = new RestartRequired
}

class RestartRequired extends Information("Restart required")