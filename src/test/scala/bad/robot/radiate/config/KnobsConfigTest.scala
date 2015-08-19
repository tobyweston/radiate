package bad.robot.radiate.config

import java.io.File

import bad.robot.radiate.ConfigurationError
import bad.robot.radiate.config.KnobsConfig._
import knobs.{ClassPathResource, FileResource, Required}
import org.specs2.matcher.DisjunctionMatchers._
import org.specs2.mutable.Specification

class KnobsConfigTest extends Specification {

  "Can be loaded" >> {
    load(Required(ClassPathResource("bad/robot/radiate/teamcity/example.cfg"))) must be_\/-
  }

  "Retrieve some configuration items" >> {
    load(Required(ClassPathResource("bad/robot/radiate/teamcity/example.cfg"))) must be_\/-.like { case config => {
      config.url must some
      config.username must some
      config.password must some
      config.authorisation must some
      config.projects must beAnInstanceOf[List[String]]
    }}
  }

  "Retrieve the values configuration items" >> {
    load(Required(ClassPathResource("bad/robot/radiate/teamcity/example.cfg"))) must be_\/-.like { case config => {
      config.url must_== Some("http://example.com:8111")
      config.username must_== Some("barry")
      config.password must_== Some("secret")
      config.authorisation must_== Some("basic")
      config.projects must_== List("one", "two")
    }}
  }

  "How are empty values handled" >> {
    load(Required(ClassPathResource("bad/robot/radiate/teamcity/example-without-user-info.cfg"))) must be_\/-.like { case config => {
      config.username must some("")
      config.password must some("  ")
    }}
  }

  "Example failure messages" >> {
    load(Required(FileResource(new File("/foo.cfg")))) must be_-\/(ConfigurationError("/foo.cfg (No such file or directory)"))
    load(Required(FileResource(new File(sys.props("user.home"))))) must be_-\/.like {
      case error: ConfigurationError => error.details must contain(s"${sys.props("user.home")} (Is a directory)")
    }
    load(Required(ClassPathResource("not/present/radiate.cfg"))) must be_-\/(ConfigurationError("not/present/radiate.cfg not found on classpath"))
    load(Required(ClassPathResource("bad/robot/radiate/teamcity/bad-example.cfg"))) must be_-\/.like {
      case error: ConfigurationError => error.details must contain("expected configuration")
    }
    load(Required(ClassPathResource("bad/robot/radiate/teamcity/bad-example-missing-fields.cfg"))) must be_-\/.like {
      case error: ConfigurationError => error.details must contain("expected '}', comment, newline, or whitespace")
    }
  }
}
