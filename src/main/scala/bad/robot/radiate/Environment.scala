package bad.robot.radiate

object EnvironmentS {
  def getEnvironmentVariable(variable: String): String = {
    if (System.getenv(variable) == null)
      throw new IllegalArgumentException(s"Please set environment variable '$variable'")
    System.getenv(variable)
  }

  def getEnvironmentVariable(variable: String, defaultValue: String): String = {
    if (System.getenv(variable) == null)
      return defaultValue
    System.getenv(variable)
  }
}
