package bad.robot.radiate.teamcity

import bad.robot.http.CommonHttpClient
import bad.robot.http.configuration.BasicAuthCredentials._
import bad.robot.radiate.FunctionInterfaceOps.toHypermedia

case class BasicAuthConfigurationS(server: ServerS, username: UsernameS, password: PasswordS) {
  def applyTo(client: CommonHttpClient) {
    client.`with`(basicAuth(username.asSimpleHttp, password.asSimpleHttp, server.urlFor("/")))
  }
}