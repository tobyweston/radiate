package bad.robot.radiate.teamcity

import bad.robot.http.{Header, HttpResponse}
import bad.robot.radiate.teamcity.JsonResponseS._

object JsonResponseS {
  private def isJson(response: HttpResponse): Boolean = {
    import scala.collection.JavaConverters._
    response.getHeaders.asScala.find(json) match {
      case Some(_) => true
      case None => false
    }
  }

  private def json: Header => Boolean = {
    header => header.name().equalsIgnoreCase("content-type") && header.value().contains("application/json")
  }
}

class JsonResponseS(response: HttpResponse) {

  if (!isJson(response)) throw new UnexpectedContentTypeS(response)

  def body = response.getContent.asString
}