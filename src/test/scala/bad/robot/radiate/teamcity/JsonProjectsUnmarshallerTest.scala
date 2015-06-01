package bad.robot.radiate.teamcity

import bad.robot.http.HeaderList._
import bad.robot.http.{HeaderPair, HttpResponse}
import bad.robot.radiate.FunctionInterfaceOps.toMessageContent
import org.scalamock.specs2.IsolatedMockFactory
import org.specs2.mutable.Specification
import bad.robot.radiate.specs2.iterableAsResult

class JsonProjectsUnmarshallerTestS extends Specification with IsolatedMockFactory {

  val response = stub[HttpResponse]
  val unmarshaller = new JsonProjectsUnmarshallerS

  "Unmarshalls Http Response" >> {
    val json = """"{
                 |  "project": [
                 |    {
                 |      "id": "_Root",
                 |      "name": "<Root project>",
                 |      "href": "/guestAuth/app/rest/projects/id:_Root"
                 |    },
                 |    {
                 |      "id": "simple_excel",
                 |      "name": "simple-excel",
                 |      "href": "/guestAuth/app/rest/projects/id:simple_excel"
                 |    }
                 |  ]
                 |}""".stripMargin

    (response.getContent _).when().returns(json)
    (response.getHeaders _).when().returns(headers(HeaderPair.header("content-type", "application/json")))

    val projects = unmarshaller.unmarshall(response)
    projects must contain(allOf(
      new ProjectScala("_Root", "<Root project>", "/guestAuth/app/rest/projects/id:_Root"),
      new ProjectScala("simple_excel", "simple-excel", "/guestAuth/app/rest/projects/id:simple_excel")
    ).inOrder)
  }

  "Unmarshall empty Http Response without shitting itself" >> {
    (response.getContent _).when().returns("")
    (response.getHeaders _).when().returns(headers(HeaderPair.header("content-type", "application/json")))
    unmarshaller.unmarshall(response)
  }
}