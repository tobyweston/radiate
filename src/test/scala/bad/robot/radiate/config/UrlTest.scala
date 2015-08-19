package bad.robot.radiate.config

import bad.robot.radiate.UrlSyntax
import bad.robot.radiate.config.Url._
import org.specs2.mutable.Specification
import java.net.URL

import org.specs2.matcher.DisjunctionMatchers._

import scalaz.{Failure, Success}

class UrlTest extends Specification {

  "Invalid URLs" >> {
    Url.validate(null) must_== Failure("No Url was found")
    Url.validate(Some("")) must_== Failure("No Url was found")
    Url.validate(Some("  ")) must_== Failure("No Url was found")
    Url.validate(Some("http://")) must_== Failure("Not a valid Url, please add a host")
    Url.validate(Some("example.com")) must_== Failure("example.com is not a valid url no protocol: example.com")
  }

  "Valid URLs" >> {
    Url.validate(Some("http://example:80")) must_== Success(new URL("http://example:80"))
    Url.validate(Some("http://example.com:80")) must_== Success(new URL("http://example.com:80"))
    Url.validate(Some("http://example.com:80/path/example")) must_== Success(new URL("http://example.com:80/path/example"))
    Url.validate(Some("https://example.com:433")) must_== Success(new URL("https://example.com:433"))
    Url.validate(Some("ftp://example.com:433")) must_== Success(new URL("ftp://example.com:433"))
  }
}



