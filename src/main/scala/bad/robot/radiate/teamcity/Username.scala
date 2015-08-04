package bad.robot.radiate.teamcity

object Username {
  def username(username: String): Username = {
    if (username == null) NoUsername else new Username(username)
  }
}

// todo implicit to convert the SimpleHTTP username
case class Username (value: String) {
  private[teamcity] def asSimpleHttp: bad.robot.http.configuration.Username = {
    bad.robot.http.configuration.Username.username(value)
  }
}

object NoUsername extends Username("no username")