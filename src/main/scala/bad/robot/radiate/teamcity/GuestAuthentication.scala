package bad.robot.radiate.teamcity

import bad.robot.http.CommonHttpClient

object GuestAuthenticationS extends BasicAuthConfigurationS(null, null, null) {
  override def applyTo(client: CommonHttpClient) { }
}