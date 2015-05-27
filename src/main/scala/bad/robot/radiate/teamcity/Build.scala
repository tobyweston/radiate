package bad.robot.radiate.teamcity

import bad.robot.radiate.{Idle, _}

case class BuildS(id: String, number: String, href: String, private val _status: String, statusText: String, startDate: String, finishDate: String, buildType: BuildTypeScala) extends TeamCityObjectS with HypermediaS with MonitorableS {

  def status: StatusS = {
    _status.toLowerCase match {
      case "success" => Ok
      case "failure" => Broken
      case _ => Unknown
    }
  }

  def activity: ActivityS = Idle

  def progress: ProgressS = new NullProgressS
}