package bad.robot.radiate.teamcity

import bad.robot.http.HeaderList._
import bad.robot.http.HeaderPair._
import bad.robot.http.{Headers, HttpResponse, MessageContent}
import bad.robot.radiate.FunctionInterfaceOps.toMessageContent
import bad.robot.radiate.ParseError
import org.scalamock.specs2.IsolatedMockFactory
import org.specs2.mutable.Specification
import org.specs2.scalaz.DisjunctionMatchers._

import scalaz.{-\/, \/-}

class JsonProjectsUnmarshallerTest extends Specification with IsolatedMockFactory {

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

    (response.getContent _: () => MessageContent).when().returns(json)
    (response.getHeaders _: () => Headers).when().returns(headers(header("content-type", "application/json")))

    unmarshaller.unmarshall(response) match {
      case -\/(error)    => ko(error.message)
      case \/-(projects) => projects must contain(allOf(
        Project("_Root", "<Root project>", "/guestAuth/app/rest/projects/id:_Root", BuildTypes(List())),
        Project("simple_excel", "simple-excel", "/guestAuth/app/rest/projects/id:simple_excel", BuildTypes(List()))
      ))
    }

  }

  "Bad JSON" >> {
    (response.getContent _: () => MessageContent).when().returns("I'm not even json")
    (response.getHeaders _: () => Headers).when().returns(headers(header("content-type", "application/json")))

    unmarshaller.unmarshall(response) must be_-\/(ParseError("Unexpected content found: I'm not even json"))
  }
}