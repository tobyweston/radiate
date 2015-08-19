package bad.robot.radiate.teamcity

import java.net.URL

import bad.robot.radiate.FunctionInterfaceOps.toHypermedia
import org.specs2.mutable.Specification
import bad.robot.radiate.UrlSyntax._
class TeamCityUrlTest extends Specification {

  "Server host can be supplied with a scheme or without" >> {
    TeamCityUrl("http://localhost:8080").urlFor("/") must_== new URL("http://localhost:8080/")
    TeamCityUrl("https://localhost:8080").urlFor("/") must_== new URL("https://localhost:8080/")
  }

  "More tests here" >> {
    ko("not implemented yet")
  }

}