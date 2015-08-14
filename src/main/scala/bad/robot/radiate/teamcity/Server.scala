package bad.robot.radiate.teamcity

import java.net.URL

import bad.robot.radiate.teamcity.TeamCityEndpoints.buildsEndpointFor
import bad.robot.radiate.{ConfigurationError, Hypermedia, UrlSyntax}
import bad.robot.radiate.OptionSyntax.NonEmptyOption

import bad.robot.radiate.UrlSyntax._

object Server {
  def apply(host: Option[String], port: Integer) = new Server(host, port)
  def apply(host: String, port: Integer) = new Server(NonEmptyOption(host), port)
}

case class BetterServer(baseUrl: URL) {

  def urlFor(endpoint: Hypermedia) = baseUrl / endpoint.href

  def urlFor(locator: BuildLocatorBuilder, authorisation: Authorisation) = {
    baseUrl / (buildsEndpointFor(authorisation).href + locator.build)
  }

}

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