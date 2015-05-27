package bad.robot.radiate.teamcity

object AuthConfiguration {
  def apply(configuration: TeamCityConfigurationS): BasicAuthConfigurationS = {
    (configuration.username, configuration.password) match {
      case (NoUsernameS, _) => GuestAuthenticationS
      case (_, NoPasswordS) => GuestAuthenticationS
      case (username, password) => BasicAuthConfigurationS(ServerS(configuration.host, configuration.port), username, password)
    }
  }
}
