package bad.robot.radiate.teamcity

import simplehttp.{Header, HttpResponse}
import bad.robot.radiate.teamcity.JsonResponse._

object JsonResponse {
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

class JsonResponse(response: HttpResponse) {

  if (!isJson(response)) throw new UnexpectedContentType(response)

  def body = response.getContent.asString
}