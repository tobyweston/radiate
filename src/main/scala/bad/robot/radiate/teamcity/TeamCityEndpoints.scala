package bad.robot.radiate.teamcity

import bad.robot.radiate.Hypermedia
import bad.robot.radiate.FunctionInterfaceOps.toHypermedia
import bad.robot.radiate.config.Authorisation

object TeamCityEndpoints {
  def projectsEndpointFor(authorisation: Authorisation): Hypermedia = s"/$authorisation/app/rest/projects"
  def buildsEndpointFor(authorisation: Authorisation): Hypermedia = s"/$authorisation/app/rest/builds/"
}
