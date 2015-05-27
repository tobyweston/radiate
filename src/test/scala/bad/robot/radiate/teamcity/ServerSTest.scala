package bad.robot.radiate.teamcity

import java.net.URL

import org.specs2.mutable.Specification
import bad.robot.radiate.FunctionInterfaceOps.toHypermedia

class ServerSTest extends Specification {

  "Server host can be supplied with a scheme or without" >> {
    ServerS("localhost", 8080).urlFor("/") must_== new URL("http://localhost:8080/")
    ServerS("http://localhost", 8080).urlFor("/") must_== new URL("http://localhost:8080/")
    ServerS("https://localhost", 8080).urlFor("/") must_== new URL("http://localhost:8080/")
  }

}
