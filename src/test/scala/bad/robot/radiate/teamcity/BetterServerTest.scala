package bad.robot.radiate.teamcity

import java.net.URL

import bad.robot.radiate.FunctionInterfaceOps.toHypermedia
import org.specs2.mutable.Specification

class BetterServerTest extends Specification {

  "Server host can be supplied with a scheme or without" >> {
    Server("localhost", 8080).urlFor("/") must_== new URL("http://localhost:8080/")
    Server("http://localhost", 8080).urlFor("/") must_== new URL("http://localhost:8080/")
    Server("https://localhost", 8080).urlFor("/") must_== new URL("https://localhost:8080/")
  }

  "More tests here" >> {
    ko("not implemented yet")
  }

}