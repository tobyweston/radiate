package bad.robot.radiate.teamcity

import bad.robot.radiate.Hypermedia
import bad.robot.radiate.FunctionInterfaceOps.toHypermedia

object TeamCityEndpoints {
  def projectsEndpointFor(authorisation: Authorisation): Hypermedia = s"/$authorisation/app/rest/projects"
  def buildsEndpointFor(authorisation: Authorisation): Hypermedia = s"/$authorisation/app/rest/builds/"
}
