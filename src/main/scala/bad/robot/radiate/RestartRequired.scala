package bad.robot.radiate

import bad.robot.radiate.monitor.Information

object RestartRequired {
  def apply() = new RestartRequired()
}

class RestartRequired extends Information("Restart required")