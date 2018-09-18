package bad.robot.radiate.teamcity

import simplehttp.HttpClient
import simplehttp.HttpClients.anApacheClient
import simplehttp.configuration.HttpTimeout.httpTimeout
import bad.robot.radiate.config.Config
import com.google.code.tempusfugit.temporal.Duration.minutes

object HttpClientFactory {
  def apply() = new HttpClientFactory()
}

class HttpClientFactory private {
  def create(config: Config): HttpClient = {
    val client = anApacheClient
    Authentication(config).applyTo(client)
    client.`with`(httpTimeout(minutes(10)))
  }
}