package bad.robot.radiate.teamcity

object Password {
  def password(password: String): Password = {
    if (password == null) NoPassword else new Password(password)
  }
}

// todo implicit to convert the SimpleHTTP password
case class Password(value: String) {
  def asSimpleHttp: bad.robot.http.configuration.Password = {
    bad.robot.http.configuration.Password.password(value)
  }
}

object NoPassword extends Password("no password")