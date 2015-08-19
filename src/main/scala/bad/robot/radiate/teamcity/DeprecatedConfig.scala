package bad.robot.radiate.teamcity

import java.net.URL
import bad.robot.radiate.Error

import scalaz.\/

trait DeprecatedConfig {
  def serverUrl: Error \/ URL
  def filter(projects: Iterable[Project]): Iterable[Project] // rename to project ids and filter outside of the function
  def username: Username
  def password: Password
  def authorisation: Authorisation
}