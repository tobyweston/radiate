package bad.robot.radiate.teamcity

import bad.robot.http.HeaderPair._
import bad.robot.http.{Header, HttpResponse}
import bad.robot.radiate.teamcity.UnexpectedContentType._
import org.apache.commons.lang3.StringUtils

object UnexpectedContentType {
  private def abbreviated(response: HttpResponse): String = {
    val body = response.getContent.asString
    StringUtils.abbreviate(body, 400)
  }

  private def contentType(response: HttpResponse): Header = {
    import scala.collection.JavaConverters._
    response.getHeaders.asScala.find(header => header.name().equalsIgnoreCase("content-type")).getOrElse(header("content-type", ""))
  }
}

class UnexpectedContentType(response: HttpResponse) extends TeamCityException(
  s"Unexpected response format '${contentType(response)}' (${response.getStatusCode} ${response.getStatusMessage}) from ${response.getOriginatingUri}\n${abbreviated(response)}"
)