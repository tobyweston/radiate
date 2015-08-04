package bad.robot.radiate.teamcity

import org.apache.commons.lang3.ClassUtils.getShortClassName
import org.apache.commons.lang3.StringUtils._
import org.apache.commons.lang3.exception.ExceptionUtils.getRootCause

object SanitisedException {
  private def removeClassPreamble(exception: Throwable): String = {
    getExpandedClassShortName(exception.getClass) + ": " + defaultString(exception.getMessage)
  }

  def getExpandedClassShortName(`type`: Class[_]): String = {
    val name = getShortClassName(`type`)
    join(splitByCharacterTypeCamelCase(name), " ")
  }
}

class SanitisedException(underlying: Exception) {

  def getMessage: String = {
    if (getRootCause(underlying) != null) SanitisedException.removeClassPreamble(getRootCause(underlying))
    else underlying.getMessage
  }
}