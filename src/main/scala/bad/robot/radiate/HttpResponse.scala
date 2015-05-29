package bad.robot.radiate

object HttpResponse {
  def unapply(response: bad.robot.http.HttpResponse): Option[Int] = {
    Some(response.getStatusCode)
  }
}
