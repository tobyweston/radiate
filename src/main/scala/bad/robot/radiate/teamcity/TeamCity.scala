package bad.robot.radiate.teamcity

import bad.robot.http.HeaderList._
import bad.robot.http.HeaderPair.header
import bad.robot.http.{HttpClient, HttpResponse => SimpleHttpResponse}
import bad.robot.radiate.Sequence.sequence
import bad.robot.radiate.teamcity.BuildLocatorBuilder.{latest, running}
import bad.robot.radiate.teamcity.TeamCityEndpoints._
import bad.robot.radiate.{Unmarshaller, AggregateException, HttpResponse}

class TeamCity(server: Server, authorisation: Authorisation, http: HttpClient, projects: Unmarshaller[SimpleHttpResponse, Iterable[Project]], project: Unmarshaller[SimpleHttpResponse, Project], build: Unmarshaller[SimpleHttpResponse, Build]) {

  private val asJson = headers(header("Accept", "application/json"))

  def retrieveProjects: Iterable[Project] = {
    val url = server.urlFor(projectsEndpointFor(authorisation))
    val response = http.get(url, asJson)
    response match {
      case response @ HttpResponse(200) => projects.unmarshall(response)
      case _ => throw new UnexpectedResponse (url, response)
    }
  }

  def retrieveFullProjects(projects: Iterable[Project]): Iterable[Project] = {
    sequence(projects.par.map(expandingToFullProject).toList) match {
      case Left(errors) => throw new AggregateException(errors)
      case Right(projects) => projects
    }
  }

  def retrieveBuildTypes(projects: Iterable[Project]): Iterable[BuildType] = {
    retrieveFullProjects(projects).flatMap(_.buildTypes)
  }

  def retrieveLatestBuild(buildType: BuildType): Build = {
    val url = server.urlFor(running(buildType), authorisation)
    http.get(url, asJson) match {
      case HttpResponse(404) => retrieveBuild(latest(buildType))
      case response @ HttpResponse(200) => build.unmarshall(response)
      case response => throw new UnexpectedResponse(url, response)
    }
  }

  private def retrieveBuild(locator: BuildLocatorBuilder): Build = {
    val url = server.urlFor(locator, authorisation)
    http.get(url, asJson) match {
      case HttpResponse(404) => new NoBuild
      case response @ HttpResponse(200) => build.unmarshall(response)
      case response => throw new UnexpectedResponse(url, response)
    }
  }

  private def expandingToFullProject: Project => Either[TeamCityException, Project] = {
    project => {
      val url = server.urlFor(project)
      http.get(url, asJson) match {
        case response @ HttpResponse(200) => Right(TeamCity.this.project.unmarshall(response))
        case response => Left(new UnexpectedResponse(url, response))
      }
    }
  }
}