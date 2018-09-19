package bad.robot.radiate.travis

import bad.robot.radiate.config.Username
import simplehttp.Url

case class Project(username: Username, project: String, branch: String = "master") {
  def toUrl = Url.url(s"https://travis-ci.org/${username.value}/$project.svg?branch=$branch")
}
