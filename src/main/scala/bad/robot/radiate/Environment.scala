package bad.robot.radiate

object Environment {
  def getEnvironmentVariable(variable: String): String = {
    sys.env.get(variable) match {
      case Some(value) => value
      case None => throw new IllegalArgumentException(s"Please set environment variable '$variable'")
    }
  }

  def getEnvironmentVariable(variable: String, defaultValue: String): String = {
    sys.env.get(variable) match {
      case Some(value) => value
      case None => defaultValue
    }
  }
}
