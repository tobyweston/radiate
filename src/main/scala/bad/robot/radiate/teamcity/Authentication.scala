package bad.robot.radiate.teamcity

import bad.robot.http.CommonHttpClient
import bad.robot.http.configuration.BasicAuthCredentials._
import bad.robot.radiate.FunctionInterfaceOps.toHypermedia

object Authentication {
  def apply(configuration: TeamCityConfigurationS): BasicAuthentication = {
    (configuration.username, configuration.password) match {
      case (NoUsernameS, _) => GuestAuthenticationS
      case (_, NoPasswordS) => GuestAuthenticationS
      case (username, password) => BasicAuthentication(ServerS(configuration.host, configuration.port), username, password)
    }
  }
}

case class BasicAuthentication(server: ServerS, username: UsernameS, password: PasswordS) {
  def applyTo(client: CommonHttpClient) {
    client.`with`(basicAuth(username.asSimpleHttp, password.asSimpleHttp, server.urlFor("/")))
  }
}

object GuestAuthenticationS extends BasicAuthentication(null, null, null) {
  override def applyTo(client: CommonHttpClient) {}
}