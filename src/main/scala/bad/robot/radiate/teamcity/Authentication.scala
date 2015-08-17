package bad.robot.radiate.teamcity

import java.net.URL

import bad.robot.http.CommonHttpClient
import bad.robot.http.configuration.BasicAuthCredentials._

object Authentication {
  def apply(configuration: TeamCityConfiguration): BasicAuthentication = {
    (configuration.username, configuration.password) match {
      case (NoUsername, _) => GuestAuthenticationS
      case (_, NoPassword) => GuestAuthenticationS
      case (username, password) => BasicAuthentication(configuration.serverUrl.valueOr(error => throw new Exception(error.message)), username, password)
    }
  }
}

case class BasicAuthentication(url: URL, username: Username, password: Password) {
  def applyTo(client: CommonHttpClient) {
    client.`with`(basicAuth(username.asSimpleHttp, password.asSimpleHttp, url))
  }
}

object GuestAuthenticationS extends BasicAuthentication(null, null, null) {
  override def applyTo(client: CommonHttpClient) {}
}