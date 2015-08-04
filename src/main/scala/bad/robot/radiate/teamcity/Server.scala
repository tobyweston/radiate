package bad.robot.radiate.teamcity

import java.net.URL

import bad.robot.radiate.teamcity.TeamCityEndpoints.buildsEndpointFor
import bad.robot.radiate.{Hypermedia, Url}

case class Server (private val host: String, port: Integer) {

  private val _host = host.replace("http://", "").replace("https://", "")

  def urlFor(endpoint: Hypermedia): URL = {
    Url.url(baseUrl + endpoint.href)
  }

  def urlFor(locator: BuildLocatorBuilder, authorisation: Authorisation): URL = {
    Url.url(baseUrl + buildsEndpointFor(authorisation).href + locator.build)
  }

  private def baseUrl: String = {
    s"http://${_host}:$port"
  }
}