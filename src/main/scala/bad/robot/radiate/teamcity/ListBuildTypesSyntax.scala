package bad.robot.radiate.teamcity

import bad.robot.radiate.AggregateError
import bad.robot.radiate.Error
import bad.robot.radiate.Sequence.sequence

import scalaz.\/

object ListBuildTypesSyntax {
  implicit class ListBuildTypesOps(buildTypes: List[BuildType]) {
    def getBuilds(teamcity: TeamCity): Error \/ List[Build] = {
      sequence(buildTypes.par.map(teamcity.retrieveLatestBuild).toList).leftMap(AggregateError)
    }
  }
}
