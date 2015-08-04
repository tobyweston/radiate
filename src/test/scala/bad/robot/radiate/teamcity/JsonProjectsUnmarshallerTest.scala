package bad.robot.radiate.teamcity

import bad.robot.http.HeaderList._
import bad.robot.http.HeaderPair._
import bad.robot.http.HttpResponse
import bad.robot.radiate.FunctionInterfaceOps.toMessageContent
import bad.robot.radiate.specs2.iterableAsResult
import org.scalamock.specs2.IsolatedMockFactory
import org.specs2.mutable.Specification

class JsonProjectsUnmarshallerTestS extends Specification with IsolatedMockFactory {

  val response = stub[HttpResponse]
  val unmarshaller = new JsonProjectsUnmarshaller

  "Unmarshalls Http Response" >> {
    val json = """{
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
    (response.getHeaders _).when().returns(headers(header("content-type", "application/json")))

    val projects = unmarshaller.unmarshall(response)
    projects must contain(allOf(
      Project("_Root", "<Root project>", "/guestAuth/app/rest/projects/id:_Root", BuildTypes(List())),
      Project("simple_excel", "simple-excel", "/guestAuth/app/rest/projects/id:simple_excel", BuildTypes(List()))
    ).inOrder)

  }

  "Bad JSON" >> {
    (response.getContent _).when().returns("I'm not even json")
    (response.getHeaders _).when().returns(headers(header("content-type", "application/json")))

    unmarshaller.unmarshall(response) must throwA[Exception]
  }
}