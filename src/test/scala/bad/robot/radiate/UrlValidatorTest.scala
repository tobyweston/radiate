package bad.robot.radiate

import java.net.URL

import bad.robot.radiate.UrlValidator._
import org.specs2.mutable.Specification
import org.specs2.matcher.DisjunctionMatchers._

class UrlValidatorTest extends Specification {

  "Invalid URLs" >> {
    validate(null) must be_-\/("No Url was found")
    validate("") must be_-\/("No Url was found")
    validate("  ") must be_-\/("No Url was found")
    validate("http://") must be_-\/("Not a valid Url, please add a host")
    validate("example.com") must be_-\/("example.com is not a valid url no protocol: example.com")
  }

  "Valid URLs" >> {
    validate("http://example:80") must be_\/-(new URL("http://example:80"))
    validate("http://example.com:80") must be_\/-(new URL("http://example.com:80"))
    validate("http://example.com:80/path/example") must be_\/-(new URL("http://example.com:80/path/example"))
    validate("https://example.com:433") must be_\/-(new URL("https://example.com:433"))
    validate("ftp://example.com:433") must be_\/-(new URL("ftp://example.com:433"))
  }

  "Defaults port" >> {
    validate("http://example") must be_\/-(new URL("http://example:8111"))
    validate("http://example.com") must be_\/-(new URL("http://example.com:8111"))
    validate("http://example.com/path/example") must be_\/-(new URL("http://example.com:8111/path/example"))
  }

  "Urls can be added" >> {
    import UrlSyntax._
    new URL("http://example.com:80") / "/path/example" must_== new URL("http://example.com:80/path/example")
    new URL("http://example.com:80") / "/path/a bc" must_== new URL("http://example.com:80/path/a%20bc")
    new URL("http://example.com:80") / "path/example" must_== new URL("http://example.com:80/path/example")
  }
}
