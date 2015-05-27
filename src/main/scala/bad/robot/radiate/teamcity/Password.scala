package bad.robot.radiate.teamcity

object PasswordS {
  def password(password: String): PasswordS = {
    if (password == null) NoPasswordS else new PasswordS(password)
  }
}

// todo implicit to convert the SimpleHTTP password
case class PasswordS(value: String) {
  def asSimpleHttp: bad.robot.http.configuration.Password = {
    bad.robot.http.configuration.Password.password(value)
  }
}

object NoPasswordS extends PasswordS("no password")