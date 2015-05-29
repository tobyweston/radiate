package bad.robot.radiate.teamcity

import java.net.URL

import bad.robot.radiate.teamcity.TeamCityEndpointsS.buildsEndpointFor
import bad.robot.radiate.{HypermediaS, UrlS}

case class ServerS (private val host: String, port: Integer) {

  private val _host = host.replace("http://", "").replace("https://", "")

  def urlFor(endpoint: HypermediaS): URL = {
    UrlS.url(baseUrl + endpoint.href)
  }

  def urlFor(locator: BuildLocatorBuilderS, authorisation: AuthorisationS): URL = {
    UrlS.url(baseUrl + buildsEndpointFor(authorisation).href + locator.build)
  }

  private def baseUrl: String = {
    s"http://${_host}:$port"
  }
}