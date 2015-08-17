package bad.robot.radiate.teamcity

import bad.robot.http.HttpClient
import bad.robot.http.HttpClients.anApacheClient
import bad.robot.http.configuration.HttpTimeout.httpTimeout
import com.google.code.tempusfugit.temporal.Duration.minutes

object HttpClientFactory {
  def apply() = new HttpClientFactory()
}

class HttpClientFactory private {
  def create(configuration: TeamCityConfiguration): HttpClient = {
    val client = anApacheClient
    Authentication(configuration).applyTo(client)
    client.`with`(httpTimeout(minutes(10)))
  }
}