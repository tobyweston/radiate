package bad.robot.radiate

import java.net.URL

import bad.robot.radiate.OptionSyntax.NonEmptyOption
import bad.robot.radiate.UrlSyntax._

import scalaz.\/

object UrlValidator {
  def validate(url: String): String \/ URL = {
    \/.fromTryCatchNonFatal(new URL(url)).leftMap(e => s"${message(url, e)}")
      .ensure("Not a valid Url")(_.getHost.nonEmpty)
      .map(_.withDefaultPort(8111))
  }

  private def message(url: String, error: Throwable): String = NonEmptyOption(url) match {
    case Some(value) => s"$value is not a valid url${message(error)}"
    case None => "No Url was found"
  }

  private def message(e: Throwable): String = NonEmptyOption(e.getMessage) match {
    case Some(msg) => " " + msg.trim
    case None => ""
  }
}
