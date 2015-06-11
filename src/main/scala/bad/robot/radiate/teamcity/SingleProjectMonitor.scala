package bad.robot.radiate.teamcity

import bad.robot.radiate.AggregatorS.aggregate
import bad.robot.radiate.monitor.{InformationS, MonitoringTaskS, NonRepeatingObservableS}

class SingleProjectMonitorS(project: ProjectScala, configuration: TeamCityConfigurationS) extends NonRepeatingObservableS with MonitoringTaskS {
  private val http = new HttpClientFactoryS().create(configuration)
  private val server = new ServerS(configuration.host, configuration.port)
  private val teamcity = new TeamCityS(server, configuration.authorisation, http, new JsonProjectsUnmarshallerS, new JsonProjectUnmarshallerS, new JsonBuildUnmarshallerS)

  def run {
    try {
      val buildTypes = teamcity.retrieveBuildTypes(List(project))
      val builds = buildTypes.par.map(teamcity.retrieveLatestBuild).toList
      val aggregated = aggregate(builds)
      notifyObservers(aggregated.activity, aggregated.progress)
      notifyObservers(aggregated.status)
      notifyObservers(new InformationS(toString))
    } catch {
      case e: Exception => notifyObservers(e)
    }
  }

  override def toString = s"${project.name} (${project.id})"
}