package bad.robot.radiate.teamcity

import bad.robot.radiate.HypermediaS
import bad.robot.radiate.FunctionInterfaceOps.toHypermedia

object TeamCityEndpointsS {
  def projectsEndpointFor(authorisation: AuthorisationS): HypermediaS = s"/$authorisation/app/rest/projects"
  def buildsEndpointFor(authorisation: AuthorisationS): HypermediaS = s"/$authorisation/app/rest/builds/"
}
