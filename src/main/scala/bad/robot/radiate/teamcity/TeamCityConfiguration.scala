package bad.robot.radiate.teamcity

import java.net.URL
import bad.robot.radiate.Error

import scalaz.\/

trait TeamCityConfiguration {
  def server: Error \/ URL
  def host: Option[String]
  def port: Integer
  def filter(projects: Iterable[Project]): Iterable[Project]
  def username: Username
  def password: Password
  def authorisation: Authorisation
}
