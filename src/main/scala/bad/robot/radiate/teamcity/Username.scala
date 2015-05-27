package bad.robot.radiate.teamcity

object UsernameS {
  def apply(username: String): UsernameS = {
    if (username == null) NoUsernameS else new UsernameS(username)
  }
}

// todo implicit to convert the SimpleHTTP username
// todo case class? duplicate apply method if I do
class UsernameS(value: String) {
  private[teamcity] def asSimpleHttp: bad.robot.http.configuration.Username = {
    bad.robot.http.configuration.Username.username(value)
  }
}

case object NoUsernameS extends UsernameS("no username")