package bad.robot.radiate

import java.net.URL

import simplehttp.{HttpResponse => SimpleHttpResponse}

sealed class Error(val message: String)

case class AggregateError(errors: List[Error]) extends Error(errors.zipWithIndex.map {
  case (error, index) => s"${index + 1}. ${error.getClass.getSimpleName}: ${error.message}"
}.mkString("One or more exception occurred, see below:\n", "\n", "\n"))
case class ParseError(details: String) extends Error(details)
case class UnexpectedResponse(url: URL, response: SimpleHttpResponse) extends Error(s"Unexpected HTTP response from $url (${response.getStatusCode}, ${response.getStatusMessage})")
case class ConfigurationError(details: String) extends Error(details)