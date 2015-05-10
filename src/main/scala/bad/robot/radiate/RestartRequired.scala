package bad.robot.radiate

import bad.robot.radiate.monitor.InformationS

object RestartRequiredS{
  def restartRequired: RestartRequiredS = new RestartRequiredS
}

class RestartRequiredS extends InformationS("Restart required")