package bad.robot.radiate.teamcity

import java.net.URL

import bad.robot.radiate.Hypermedia
import bad.robot.radiate.UrlSyntax._
import bad.robot.radiate.config.Authorisation
import bad.robot.radiate.teamcity.TeamCityEndpoints.buildsEndpointFor

case class TeamCityUrl(baseUrl: URL) {

  def urlFor(endpoint: Hypermedia) = baseUrl / endpoint.href

  def urlFor(locator: BuildLocatorBuilder, authorisation: Authorisation) = {
    baseUrl / (buildsEndpointFor(authorisation).href + locator.build)
  }
}