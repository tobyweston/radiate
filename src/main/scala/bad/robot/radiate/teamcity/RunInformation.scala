package bad.robot.radiate.teamcity

case class RunInformationS(percentageComplete: Integer, elapsedSeconds: Integer, estimatedTotalSeconds: Integer, outdated: Boolean, probablyHanging: Boolean) extends TeamCityObjectS
