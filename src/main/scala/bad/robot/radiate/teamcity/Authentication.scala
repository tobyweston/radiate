package bad.robot.radiate.teamcity

import java.net.URL

import bad.robot.http.CommonHttpClient
import bad.robot.http.configuration.BasicAuthCredentials._
import bad.robot.radiate.config._

object Authentication {
  def apply(config: Config): BasicAuthentication = {
    (config.teamcity.server.username, config.teamcity.server.password) match {
      case (None, _) => GuestAuthentication
      case (_, None) => GuestAuthentication
      case (Some(username), Some(password)) => BasicAuthentication(config.teamcity.server.url, username, password)
    }
  }
}

case class BasicAuthentication(url: URL, username: Username, password: Password) {
  import bad.robot.http._
  def applyTo(client: CommonHttpClient) {
    client.`with`(basicAuth(username, password, url))
  }
}

object GuestAuthentication extends BasicAuthentication(null, null, null) {
  override def applyTo(client: CommonHttpClient) {}
}