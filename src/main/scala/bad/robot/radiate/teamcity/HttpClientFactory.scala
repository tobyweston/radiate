package bad.robot.radiate.teamcity

import bad.robot.http.HttpClient
import bad.robot.http.HttpClients.anApacheClient
import bad.robot.http.configuration.HttpTimeout.httpTimeout
import com.google.code.tempusfugit.temporal.Duration.minutes

class HttpClientFactoryS {
  def create(configuration: TeamCityConfigurationS): HttpClient = {
    val client = anApacheClient
    BasicAuthConfigurationS(configuration).applyTo(client)
    client.`with`(httpTimeout(minutes(10)))
  }
}