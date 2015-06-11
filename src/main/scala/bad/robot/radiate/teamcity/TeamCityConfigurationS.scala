package bad.robot.radiate.teamcity

trait TeamCityConfigurationS {
  def host: String
  def port: Integer
  def filter(projects: Iterable[ProjectScala]): Iterable[ProjectScala]
  def username: UsernameS
  def password: PasswordS
  def authorisation: AuthorisationS
}
