package bad.robot.radiate.teamcity

import bad.robot.http.HeaderList._
import bad.robot.http.{HeaderPair, HttpResponse}
import org.scalamock.specs2.IsolatedMockFactory
import org.specs2.mutable.Specification
import bad.robot.radiate.FunctionInterfaceOps.toMessageContent

class JsonProjectUnmarshallerTestS extends Specification with IsolatedMockFactory {

  val response = stub[HttpResponse]
  val unmarshaller = new JsonProjectUnmarshallerS

  "Unmarshall Project with build types" >> {
    (response.getContent _).when().returns(projectJson)
    (response.getHeaders _).when().returns(headers(HeaderPair.header("content-type", "application/json")))

    val buildTypes = new BuildTypesScala(List(
      new BuildTypeScala("example_1", "First", "/guestAuth/app/rest/buildTypes/id:example_1", "example", "example"),
      new BuildTypeScala("example_2", "Second", "/guestAuth/app/rest/buildTypes/id:example_2", "example", "example")
    ))
    val project = unmarshaller.unmarshall(response).asInstanceOf[FullProjectS]
    project must_== new FullProjectS("example", "example", "/guestAuth/app/rest/projects/id:example", buildTypes)
  }

  val projectJson = """{
                      |  "id": "example",
                      |  "name": "example",
                      |  "href": "/guestAuth/app/rest/projects/id:example",
                      |  "description": "",
                      |  "archived": false,
                      |  "webUrl": "http://localhost:8111/project.html?projectId=example",
                      |  "parentProject": {
                      |    "id": "_Root",
                      |    "name": "<Root project>",
                      |    "href": "/guestAuth/app/rest/projects/id:_Root"
                      |  },
                      |  "buildTypes": {
                      |    "buildType": [
                      |      {
                      |        "id": "example_1",
                      |        "name": "First",
                      |        "href": "/guestAuth/app/rest/buildTypes/id:example_1",
                      |        "projectName": "example",
                      |        "projectId": "example",
                      |        "webUrl": "http://localhost:8111/viewType.html?buildTypeId=example_1"
                      |      },
                      |      {
                      |        "id": "example_2",
                      |        "name": "Second",
                      |        "href": "/guestAuth/app/rest/buildTypes/id:example_2",
                      |        "projectName": "example",
                      |        "projectId": "example",
                      |        "webUrl": "http://localhost:8111/viewType.html?buildTypeId=example_2"
                      |      }
                      |    ]
                      |  },
                      |  "templates": {
                      |    "buildType": []
                      |  },
                      |  "parameters": {
                      |    "property": []
                      |  },
                      |  "vcsRoots": {
                      |    "href": "/guestAuth/app/rest/vcs-roots?locator=project:(id:example)"
                      |  },
                      |  "projects": {
                      |    "project": []
                      |  }
                      |}""".stripMargin
}