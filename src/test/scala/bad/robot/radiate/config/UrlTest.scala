package bad.robot.radiate.config

import java.net.URL

import org.specs2.mutable.Specification

import scalaz.{Failure, Success}

class UrlTest extends Specification {

  "Invalid URLs" >> {
    Url.validate(None) must_== Failure("No Url was found or it was empty")
    Url.validate(Some("")) must_== Failure("No Url was found or it was empty")
    Url.validate(Some("  ")) must_== Failure("No Url was found or it was empty")
    Url.validate(Some("http://")) must_== Failure("Not a valid Url, no host specified")
    Url.validate(Some("example.com")) must_== Failure("example.com is not a valid url no protocol: example.com")
  }

  "Valid URLs" >> {
    Url.validate(Some("http://example:80")) must_== Success(new URL("http://example:80"))
    Url.validate(Some("http://example.com:80")) must_== Success(new URL("http://example.com:80"))
    Url.validate(Some("http://example.com:80/path/example")) must_== Success(new URL("http://example.com:80/path/example"))
    Url.validate(Some("https://example.com:433")) must_== Success(new URL("https://example.com:433"))
    Url.validate(Some("ftp://example.com:433")) must_== Success(new URL("ftp://example.com:433"))
  }

  "Defaults port" >> {
    Url.validate(Some("http://example")) must_== Success(new URL("http://example:8111"))
    Url.validate(Some("http://example.com")) must_== Success(new URL("http://example.com:8111"))
    Url.validate(Some("http://example.com/path/example")) must_== Success(new URL("http://example.com:8111/path/example"))
  }

}



