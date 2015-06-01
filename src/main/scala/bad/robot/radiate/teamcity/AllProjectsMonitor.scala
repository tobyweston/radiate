package bad.robot.radiate.teamcity

import bad.robot.radiate.AggregatorS.aggregate
import bad.robot.radiate.monitor.{InformationS, MonitoringTaskS, NonRepeatingObservableS}

class AllProjectsMonitorS(configuration: TeamCityConfigurationS) extends NonRepeatingObservableS with MonitoringTaskS {
  private val http = new HttpClientFactoryS().create(configuration)
  private val server = new ServerS(configuration.host, configuration.port)
  private val teamcity = new TeamCityS(server, configuration.authorisation, http, new JsonProjectsUnmarshallerS, new JsonProjectUnmarshallerS, new JsonBuildUnmarshallerS)

  private var monitored = List("unknown")

  def run {
    try {
      val projects = configuration.filter(teamcity.retrieveProjects)
      monitored = projects.map(_.toString).toList
      val buildTypes = teamcity.retrieveBuildTypes(projects)
      val builds = buildTypes.par.map(teamcity.retrieveLatestBuild).toList
      val aggregated = aggregate(builds)
      notifyObservers(aggregated.activity, aggregated.progress)
      notifyObservers(aggregated.status)
      notifyObservers(new InformationS(toString))
    }
    catch {
      case e: Exception => {
        notifyObservers(e)
      }
    }
  }

  override def toString: String = {
    s"monitoring ${monitored.mkString("\r\n")} as a single aggregate"
  }
}