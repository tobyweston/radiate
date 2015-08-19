package bad.robot.radiate.config

import java.net.URL

import bad.robot.radiate.Error
import bad.robot.radiate.UrlSyntax._
import bad.robot.radiate.OptionSyntax._
import bad.robot.radiate.teamcity.{Username, Authorisation, Password}
import scalaz.Validation.FlatMap._

import scalaz.syntax.std.option._
import scalaz.{Validation, \/}

object Url {
  def validate(url: Option[String]): Validation[String, URL] = {
    for {
      url    <- NonEmptyOption(url).toSuccess("No Url was found or it was empty")
      result <- Validation.fromTryCatchNonFatal(new URL(url)).leftMap(e => s"$url is not a valid url${message(e)}").ensure("Not a valid Url, no host specified")(_.getHost.nonEmpty)
    } yield result.withDefaultPort(8111)
  }

  private def message(e: Throwable): String = NonEmptyOption(e.getMessage) match {
    case Some(msg) => " " + msg.trim
    case None => ""
  }
}
