package bad.robot.radiate.teamcity

import java.net.URL

import org.specs2.mutable.Specification
import bad.robot.radiate.FunctionInterfaceOps.toHypermedia

class ServerTest extends Specification {

  "Server can get basic URL" >> {
    new BetterServer(new URL("http://localhost:8080")).urlFor("/") must_== new URL("http://localhost:8080/")
    new BetterServer(new URL("https://localhost:8080")).urlFor("/") must_== new URL("https://localhost:8080/")
  }

  "Server can get URL for build locator" >> {
    ko("not implemented yet")
  }

}