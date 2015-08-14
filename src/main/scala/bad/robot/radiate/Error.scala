package bad.robot.radiate

import java.net.URL

import bad.robot.http.{HttpResponse => SimpleHttpResponse}

sealed class Error(val message: String)

case class AggregateError(errors: List[Error]) extends Error("???")
case class ParseError(details: String) extends Error(details)
case class UnexpectedResponse(url: URL, response: SimpleHttpResponse) extends Error(
  s"Unexpected HTTP response from $url (${response.getStatusCode}, ${response.getStatusMessage})"
)
case class ConfigurationError(details: String) extends Error(details)