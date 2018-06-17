package bad.robot.radiate.teamcity

import java.net.URL

import bad.robot.radiate.FunctionInterfaceOps.toHypermedia
import bad.robot.radiate.UrlSyntax._
import bad.robot.radiate.config.{BasicAuthorisation, GuestAuthorisation}
import org.specs2.mutable.Specification

class TeamCityUrlTest extends Specification {

  "Server host can be supplied with a scheme or without" >> {
    TeamCityUrl("http://localhost:8080").urlFor("/") must_== new URL("http://localhost:8080/")
    TeamCityUrl("https://localhost:8080").urlFor("/") must_== new URL("https://localhost:8080/")
  }

  "Build locator for latest build" >> {
    val buildType = BuildType("id", "name", "href", "project", "projectId")
    TeamCityUrl("http://example.com:8111").urlFor(BuildLocatorBuilder.latest(buildType), BasicAuthorisation) must_== new URL("http://example.com:8111/httpAuth/app/rest/builds/buildType:id")
    TeamCityUrl("http://example.com:8111").urlFor(BuildLocatorBuilder.latest(buildType), GuestAuthorisation) must_== new URL("http://example.com:8111/guestAuth/app/rest/builds/buildType:id")
  }

  "Build locator for running build" >> {
    val buildType = BuildType("id", "name", "href", "project", "projectId")
    TeamCityUrl("http://example.com:8111").urlFor(BuildLocatorBuilder.running(buildType), BasicAuthorisation) must_== new URL("http://example.com:8111/httpAuth/app/rest/builds/buildType:id,running:true")
    TeamCityUrl("http://example.com:8111").urlFor(BuildLocatorBuilder.running(buildType), GuestAuthorisation) must_== new URL("http://example.com:8111/guestAuth/app/rest/builds/buildType:id,running:true")
  }

}