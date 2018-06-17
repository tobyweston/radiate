package bad.robot.radiate.config

import java.io.File

import bad.robot.radiate.ConfigurationError
import bad.robot.radiate.config.KnobsConfig._
import knobs.{ClassPathResource, FileResource, Required}
import org.specs2.scalaz.DisjunctionMatchers._
import org.specs2.mutable.Specification
import scala.concurrent.ExecutionContext.Implicits.global     // todo replace with explicit one

class KnobsConfigTest extends Specification {

  "Can be loaded" >> {
    load(Required(ClassPathResource("bad/robot/radiate/teamcity/example.cfg"))) must be_\/-
  }

  "Retrieve some configuration items" >> {
    load(Required(ClassPathResource("bad/robot/radiate/teamcity/example.cfg"))) must beRightDisjunction.like { case config => {
      config.url must some
      config.username must some
      config.password must some
      config.authorisation must some
      config.projects must beAnInstanceOf[List[String]]
    }}
  }

  "Retrieve 'none' for configuration items" >> {
    load(Required(ClassPathResource("bad/robot/radiate/teamcity/example-empty.cfg"))) must beRightDisjunction.like { case config => {
      config.url must none
      config.username must none
      config.password must none
      config.authorisation must none
      config.projects must_== List()
    }}
  }

  "Retrieve the values configuration items" >> {
    load(Required(ClassPathResource("bad/robot/radiate/teamcity/example.cfg"))) must beRightDisjunction.like { case config => {
      config.url must_== Some("http://example.com:8111")
      config.username must_== Some("barry")
      config.password must_== Some("secret")
      config.authorisation must_== Some("basic")
      config.projects must_== List("one", "two")
    }}
  }

  "How are empty values handled" >> {
    load(Required(ClassPathResource("bad/robot/radiate/teamcity/example-without-user-info.cfg"))) must beRightDisjunction.like { case config => {
      config.username must some("")
      config.password must some("  ")
    }}
  }

  "Example failure messages" >> {
    load(Required(FileResource(new File("/foo.cfg")))) must beLeftDisjunction.like {
      case error: ConfigurationError => error.details must contain("/foo.cfg (No such file or directory)")
    }
    load(Required(FileResource(new File(sys.props("user.home"))))) must beLeftDisjunction.like {
      case error: ConfigurationError => error.details must contain(s"${sys.props("user.home")} (Is a directory)")
    }
    load(Required(ClassPathResource("not/present/radiate.cfg"))) must beLeftDisjunction.like {
      case error: ConfigurationError => error.details must contain("not/present/radiate.cfg not found on classpath")
    }
    load(Required(ClassPathResource("bad/robot/radiate/teamcity/bad-example.cfg"))) must beLeftDisjunction.like {
      case error: ConfigurationError => error.details must contain("expected configuration")
    }
    load(Required(ClassPathResource("bad/robot/radiate/teamcity/bad-example-missing-fields.cfg"))) must beLeftDisjunction.like {
      case error: ConfigurationError => error.details must contain("expected comment, newline, value, or whitespace")
    }
  }

  def getEnvironmentVariableStub(url: Option[String], username: Option[String], password: Option[String]): (String) => Option[String] = {
    case "TEAMCITY_URL" => url
    case "TEAMCITY_USERNAME" => username
    case "TEAMCITY_PASSWORD" => password
    case _ => ???
  }

  "Invalid URL when attempting to bootstrap config from environment variables" >> {
    val error = create(getEnvironmentVariableStub(Some("example.com:8912"), Some("bob"), Some("secret")))
    error must be_-\/(ConfigurationError("Invalid environment variable for 'TEAMCITY_URL'. example.com:8912 is not a valid url unknown protocol: example.com"))
  }

  "Missing URL when attempting to bootstrap config from environment variables" >> {
    val error = create(getEnvironmentVariableStub(None, Some("bob"), Some("secret")))
    error must be_-\/(ConfigurationError("Invalid environment variable for 'TEAMCITY_URL'. No Url was found or it was empty"))

    val anotherError = create(getEnvironmentVariableStub(Some(""), Some("bob"), Some("secret")))
    anotherError must be_-\/(ConfigurationError("Invalid environment variable for 'TEAMCITY_URL'. No Url was found or it was empty"))
  }

}
