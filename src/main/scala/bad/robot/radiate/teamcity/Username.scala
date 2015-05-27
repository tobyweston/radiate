package bad.robot.radiate.teamcity

object UsernameS {
  def username(username: String): UsernameS = {
    if (username == null) NoUsernameS else new UsernameS(username)
  }
}

// todo implicit to convert the SimpleHTTP username
case class UsernameS (value: String) {
  private[teamcity] def asSimpleHttp: bad.robot.http.configuration.Username = {
    bad.robot.http.configuration.Username.username(value)
  }
}

object NoUsernameS extends UsernameS("no username")