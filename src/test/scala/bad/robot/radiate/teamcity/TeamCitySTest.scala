package bad.robot.radiate.teamcity

import java.net.URL

import bad.robot.http.EmptyHeaders._
import bad.robot.http.HeaderList._
import bad.robot.http.HeaderPair._
import bad.robot.http.{Headers, HttpClient, HttpResponse, StringHttpResponse}
import bad.robot.radiate.Unmarshaller
import org.scalamock.specs2.IsolatedMockFactory
import org.specs2.mutable.Specification

class TeamCitySTest extends Specification with IsolatedMockFactory {

  private val accept = headers(header("Accept", "application/json"))
  private val http = mock[HttpClient]

  private val Ok = new StringHttpResponse(200, "OK", "", emptyHeaders, "http://example.com")
  private val AnotherOk = new StringHttpResponse(200, "OK", "", emptyHeaders, "http://example.com")
  private val Error = new StringHttpResponse(500, "Yuk", "", emptyHeaders, "http://example.com")
  private val NotFound = new StringHttpResponse(404, "Not Found", "", emptyHeaders, "http://example.com")

  private val projects = AnyS.projects

  private val projectsUnmarshaller = mock[Unmarshaller[HttpResponse, Iterable[ProjectScala]]]
  private val projectUnmarshaller = mock[Unmarshaller[HttpResponse, ProjectScala]]
  private val buildUnmarshaller = mock[Unmarshaller[HttpResponse, BuildS]]
  private val teamcity = new TeamCityS(new ServerS("example.com", 8111), GuestAuthorisationS, http, projectsUnmarshaller, projectUnmarshaller, buildUnmarshaller)

  "Should retrieve projects" >> {
    (http.get(_: URL, _: Headers)).expects(new URL("http://example.com:8111/guestAuth/app/rest/projects"), accept).once.returning(Ok)
    (projectsUnmarshaller.unmarshall _).expects(Ok).once.returning(projects)

    teamcity.retrieveProjects must_== projects
  }

  "Should handle Http error when retrieving projects" >> {
    (http.get(_: URL, _: Headers)).expects(*, *).once.returning(Error)
    teamcity.retrieveProjects must throwAn[UnexpectedResponseS]
  }

}
