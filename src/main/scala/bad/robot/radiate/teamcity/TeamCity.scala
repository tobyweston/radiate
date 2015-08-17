package bad.robot.radiate.teamcity

import java.net.URL

import bad.robot.http.HeaderList._
import bad.robot.http.HeaderPair.header
import bad.robot.http.{HttpClient, HttpResponse => SimpleHttpResponse}
import bad.robot.radiate.Sequence.sequence
import bad.robot.radiate.teamcity.BuildLocatorBuilder.{latest, running}
import bad.robot.radiate.teamcity.TeamCityEndpoints._
import bad.robot.radiate._
import bad.robot.radiate.Error
import bad.robot.radiate.teamcity.HttpClientSyntax._

import scalaz.{-\/, \/-, \/}
import scalaz.syntax.either._

object HttpClientSyntax {
  implicit class HttpClientOps(http: HttpClient) {
    implicit def get[A](url: URL, success: SimpleHttpResponse => Error \/ A): Error \/ A = {
      val response = http.get(url, headers(header("Accept", "application/json")))
      response match {
        case response@HttpResponse(200) => success(response)
        case _ => UnexpectedResponse(url, response).left
      }
    }
  }
}

object TeamCity {
  def apply(server: TeamCityUrl, authorisation: Authorisation, http: HttpClient, projects: Unmarshaller[SimpleHttpResponse, Iterable[Project]], project: Unmarshaller[SimpleHttpResponse, Project], build: Unmarshaller[SimpleHttpResponse, Build]) = {
    new TeamCity(server, authorisation, http, projects, project, build)
  }
}

class TeamCity(server: TeamCityUrl, authorisation: Authorisation, http: HttpClient, projects: Unmarshaller[SimpleHttpResponse, Iterable[Project]], project: Unmarshaller[SimpleHttpResponse, Project], build: Unmarshaller[SimpleHttpResponse, Build]) {

  private val asJson = headers(header("Accept", "application/json"))

  def retrieveProjects: Error \/ Iterable[Project] = {
    val url = server.urlFor(projectsEndpointFor(authorisation))
    http.get(url, projects.unmarshall _)
  }

  def retrieveFullProjects(projects: Iterable[Project]): Error \/ List[Project] = {
    sequence(projects.par.map(expandingToFullProject).toList).leftMap(AggregateError)
  }

  def retrieveBuildTypes(projects: Iterable[Project]): Error \/ List[BuildType] = {
    retrieveFullProjects(projects).map(_.flatMap(_.buildTypes.list))
  }

  def retrieveLatestBuild(buildType: BuildType): Error \/ Build = {
    val url = server.urlFor(running(buildType), authorisation)
    http.get(url, asJson) match {
      case HttpResponse(404) => retrieveBuild(latest(buildType))
      case response @ HttpResponse(200) => build.unmarshall(response)
      case response => throw new UnexpectedHttpResponse(url, response)
    }
  }

  private def retrieveBuild(locator: BuildLocatorBuilder): Error \/ Build = {
    val url = server.urlFor(locator, authorisation)
    http.get(url, asJson) match {
      case HttpResponse(404) => \/-(new NoBuild)
      case response @ HttpResponse(200) => build.unmarshall(response)
      case response => -\/(UnexpectedResponse(url, response))
    }
  }

  private def expandingToFullProject: Project => Error \/ Project = project => {
    http.get(server.urlFor(project), TeamCity.this.project.unmarshall _)
  }
}