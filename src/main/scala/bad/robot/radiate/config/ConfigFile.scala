package bad.robot.radiate.config

private[config] trait ConfigFile {
  def url: Option[String]
  def projects: List[String]
  def username: Option[String]
  def password: Option[String]
  def authorisation: Option[String]
}
