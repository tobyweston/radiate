package bad.robot.radiate.teamcity

object PasswordS {
  def apply(password: String): PasswordS = {
    if (password == null) NoPasswordS else new PasswordS(password)
  }
}

// todo implicit to convert the SimpleHTTP password
// todo case class? duplicate apply method if I do
class PasswordS(value: String) {
  def asSimpleHttp: bad.robot.http.configuration.Password = {
    bad.robot.http.configuration.Password.password(value)
  }
}

case object NoPasswordS extends PasswordS("no password")