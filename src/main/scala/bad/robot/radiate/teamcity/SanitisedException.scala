package bad.robot.radiate.teamcity

import org.apache.commons.lang3.ClassUtils.getShortClassName
import org.apache.commons.lang3.StringUtils._
import org.apache.commons.lang3.exception.ExceptionUtils.getRootCause

object SanitisedExceptionS {
  private def removeClassPreamble(exception: Throwable): String = {
    getExpandedClassShortName(exception.getClass) + ": " + defaultString(exception.getMessage)
  }

  def getExpandedClassShortName(`type`: Class[_]): String = {
    val name = getShortClassName(`type`)
    join(splitByCharacterTypeCamelCase(name), " ")
  }
}

class SanitisedExceptionS(underlying: Exception) {

  def getMessage: String = {
    if (getRootCause(underlying) != null) SanitisedExceptionS.removeClassPreamble(getRootCause(underlying)) 
    else underlying.getMessage
  }
}