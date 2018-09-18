package bad.robot.radiate

object HttpResponse {
  def unapply(response: simplehttp.HttpResponse): Option[Int] = {
    Some(response.getStatusCode)
  }
}
