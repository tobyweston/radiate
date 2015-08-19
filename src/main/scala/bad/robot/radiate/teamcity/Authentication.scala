package bad.robot.radiate.teamcity

import java.net.URL

import bad.robot.http.CommonHttpClient
import bad.robot.http.configuration.BasicAuthCredentials._
import bad.robot.radiate.config.Config

object Authentication {
  def apply(config: Config): BasicAuthentication = {
    (config.username, config.password) match {
      case (NoUsername, _) => GuestAuthenticationS
      case (_, NoPassword) => GuestAuthenticationS
      case (username, password) => BasicAuthentication(config.url, username, password)
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