package bad.robot.radiate.teamcity

import java.net.URL

import bad.robot.radiate.Hypermedia
import bad.robot.radiate.OptionSyntax.NonEmptyOption
import bad.robot.radiate.teamcity.TeamCityEndpoints.buildsEndpointFor

object Server {
  def apply(host: Option[String], port: Integer) = new Server(host, port)
  def apply(host: String, port: Integer) = new Server(NonEmptyOption(host), port)
}


// TODO remove me
class Server(host: Option[String], port: Integer) {

  private val _host = host.map(_.replace("http://", "").replace("https://", ""))

  def urlFor(endpoint: Hypermedia): URL = {
    url(baseUrl + endpoint.href)
  }

  def urlFor(locator: BuildLocatorBuilder, authorisation: Authorisation): URL = {
    url(baseUrl + buildsEndpointFor(authorisation).href + locator.build)
  }

  private def baseUrl: String = {
    s"http://${_host}:$port"
  }

  private def url(url: String): URL = new URL(url.replace(" ", "%20"))
}