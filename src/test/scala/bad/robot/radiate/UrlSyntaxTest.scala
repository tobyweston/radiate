package bad.robot.radiate

import java.net.URL

import org.specs2.mutable.Specification

class UrlSyntaxTest extends Specification {

  import UrlSyntax._

  "Defaults port" >> {
    new URL("http://example").withDefaultPort(8111) must_== new URL("http://example:8111")
    new URL("http://example.com").withDefaultPort(8111) must_== new URL("http://example.com:8111")
    new URL("http://example.com/path/example").withDefaultPort(8111) must_== new URL("http://example.com:8111/path/example")
    new URL("http://example.com:80").withDefaultPort(8111) must_== new URL("http://example.com:80")
  }

  "Urls can be added" >> {
    new URL("http://example.com:80") / "/path/example" must_== new URL("http://example.com:80/path/example")
    new URL("http://example.com:80") / "/path/a bc" must_== new URL("http://example.com:80/path/a%20bc")
    new URL("http://example.com:80") / "path/example" must_== new URL("http://example.com:80/path/example")
  }
}
