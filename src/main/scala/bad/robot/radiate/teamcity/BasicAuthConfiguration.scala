package bad.robot.radiate.teamcity

import bad.robot.http.CommonHttpClient
import bad.robot.http.configuration.BasicAuthCredentials
import bad.robot.radiate.FunctionInterfaceOps.toHypermedia

object BasicAuthConfigurationS {
  def apply(configuration: TeamCityConfigurationS): BasicAuthConfigurationS = {
//    (configuration.username, configuration.password) match {
//      case (NoUsernameS, _) => new GuestAuthenticationS
//      case (_, noPassword) => new GuestAuthenticationS
//    }

    val username = configuration.username
    val password = configuration.password
    if ((username == NoUsernameS) || (password == NoPasswordS))
      return GuestAuthenticationS
    val host = configuration.host
    val port = configuration.port
    val server = new ServerS(host, port)
    new BasicAuthConfigurationS(server, username, password)
  }
}

case class BasicAuthConfigurationS (server: ServerS, username: UsernameS, password: PasswordS) {
  def applyTo(client: CommonHttpClient) {
    client.`with`(BasicAuthCredentials.basicAuth(username.asSimpleHttp, password.asSimpleHttp, server.urlFor("/")))
  }
}