package bad.robot.radiate.config

import org.specs2.mutable.Specification

class TemplateTest extends Specification {

  "Fully populated config file template" >> {
    Template(new ConfigFile {
      def url: Option[String] = Some("http://teamcity.com:8111")
      def username: Option[String] = Some("bob")
      def password: Option[String] = Some("secret")
      def authorisation: Option[String] = Some("basic")
      def projects: List[String] = List("projectA", "projectB")
    }) must_==
      """
        |server {
        |    url = "http://teamcity.com:8111"
        |    username = "bob"
        |    password = "secret"
        |    authorisation = "basic"   # guest | basic
        |}
        |
        |projects = ["projectA", "projectB"]
        |""".stripMargin
  }

  "Partially populated (but valid) config file template" >> {
    Template(new ConfigFile {
      def url: Option[String] = Some("http://teamcity.com:8111")
      def username: Option[String] = None
      def password: Option[String] = None
      def authorisation: Option[String] = Some("basic")
      def projects: List[String] = List()
    }) must_==
      """
        |server {
        |    url = "http://teamcity.com:8111"
        |    # username = "???"
        |    # password = "???"
        |    authorisation = "basic"   # guest | basic
        |}
        |
        |projects = [ ]
        |""".stripMargin
  }

  "Empty config file template" >> {
    Template(new ConfigFile {
      def url: Option[String] = None
      def username: Option[String] = None
      def password: Option[String] = None
      def authorisation: Option[String] = None
      def projects: List[String] = List()
    }) must_==
      """
        |server {
        |    # url = "http://example.com:8111"
        |    # username = "???"
        |    # password = "???"
        |    # authorisation = "guest | basic"
        |}
        |
        |projects = [ ]
        |""".stripMargin
  }


}
