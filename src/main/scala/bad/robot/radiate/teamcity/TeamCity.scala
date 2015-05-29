package bad.robot.radiate.teamcity

import bad.robot.http.HeaderList._
import bad.robot.http.HeaderPair.header
import bad.robot.http.{HttpClient, HttpResponse => SimpleHttpResponse}
import bad.robot.radiate.Sequence.sequence
import bad.robot.radiate.teamcity.BuildLocatorBuilderS.{latest, running}
import bad.robot.radiate.teamcity.TeamCityEndpointsS._
import bad.robot.radiate.{AggregateException, HttpResponse, Unmarshaller}

class TeamCityS(server: ServerS, authorisation: AuthorisationS, http: HttpClient, projects: Unmarshaller[SimpleHttpResponse, Iterable[ProjectScala]], project: Unmarshaller[SimpleHttpResponse, ProjectScala], build: Unmarshaller[SimpleHttpResponse, BuildS]) {

  private val asJson = headers(header("Accept", "application/json"))

  def retrieveProjects: Iterable[ProjectScala] = {
    val url = server.urlFor(projectsEndpointFor(authorisation))
    val response = http.get(url, asJson)
    response match {
      case response @ HttpResponse(200) => projects.unmarshall(response)
      case _ => throw new UnexpectedResponseS (url, response)
    }
  }

  def retrieveFullProjects(projects: Iterable[ProjectScala]): Iterable[ProjectScala] = {
    sequence(projects.par.map(expandingToFullProject).toList) match {
      case Left(errors) => throw new AggregateException(errors)
      case Right(projects) => projects
    }
  }

  def retrieveBuildTypes(projects: Iterable[ProjectScala]): Iterable[BuildTypeScala] = {
    retrieveFullProjects(projects).flatten
  }

  def retrieveLatestBuild(buildType: BuildTypeScala): BuildS = {
    val url = server.urlFor(running(buildType), authorisation)
    http.get(url, asJson) match {
      case HttpResponse(404) => retrieveBuild(latest(buildType))
      case response @ HttpResponse(200) => build.unmarshall(response)
      case response => throw new UnexpectedResponseS(url, response)
    }
  }

  private def retrieveBuild(locator: BuildLocatorBuilderS): BuildS = {
    val url = server.urlFor(locator, authorisation)
    http.get(url, asJson) match {
      case HttpResponse(404) => new NoBuildS
      case response @ HttpResponse(200) => build.unmarshall(response)
      case response => throw new UnexpectedResponseS(url, response)
    }
  }

  private def expandingToFullProject: ProjectScala => Either[TeamCityExceptionS, ProjectScala] = {
    project => {
      val url = server.urlFor(project)
      http.get(url, asJson) match {
        case response @ HttpResponse(200) => Right(TeamCityS.this.project.unmarshall(response))
        case response => Left(new UnexpectedResponseS(url, response))
      }
    }
  }
}