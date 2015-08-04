package bad.robot.radiate.teamcity

trait TeamCityConfiguration {
  def host: String
  def port: Integer
  def filter(projects: Iterable[Project]): Iterable[Project]
  def username: Username
  def password: Password
  def authorisation: Authorisation
}
