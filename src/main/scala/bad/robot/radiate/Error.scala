package bad.robot.radiate

import java.net.URL

import bad.robot.http.{HttpResponse => SimpleHttpResponse}

sealed class RadiateError(val message: String)

object RadiateError {
  type Error = RadiateError
}

case class AggregateError(errors: List[RadiateError]) extends RadiateError("???")
case class ParseError(details: String) extends RadiateError(details)
case class UnexpectedResponse(url: URL, response: SimpleHttpResponse) extends RadiateError(
  s"Unexpected HTTP response from $url (${response.getStatusCode}, ${response.getStatusMessage})"
)