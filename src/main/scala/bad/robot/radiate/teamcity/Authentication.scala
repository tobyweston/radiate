package bad.robot.radiate.teamcity

import bad.robot.http.CommonHttpClient
import bad.robot.http.configuration.BasicAuthCredentials._
import bad.robot.radiate.FunctionInterfaceOps.toHypermedia

object Authentication {
  def apply(configuration: TeamCityConfiguration): BasicAuthentication = {
    (configuration.username, configuration.password) match {
      case (NoUsername, _) => GuestAuthenticationS
      case (_, NoPassword) => GuestAuthenticationS
      case (username, password) => BasicAuthentication(Server(configuration.host, configuration.port), username, password)
    }
  }
}

case class BasicAuthentication(server: Server, username: Username, password: Password) {
  def applyTo(client: CommonHttpClient) {
    client.`with`(basicAuth(username.asSimpleHttp, password.asSimpleHttp, server.urlFor("/")))
  }
}

object GuestAuthenticationS extends BasicAuthentication(null, null, null) {
  override def applyTo(client: CommonHttpClient) {}
}