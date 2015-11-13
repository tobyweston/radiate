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
      def ecoMode: (Option[String], Option[String]) = (Some("10:00"), Some("12:00"))
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
        |
        |ui {
        |    eco-mode {
        |        start = "10:00"
        |        end = "12:00"
        |    }
        |}
        |""".stripMargin
  }

  "Partially populated (but valid) config file template" >> {
    Template(new ConfigFile {
      def url: Option[String] = Some("http://teamcity.com:8111")
      def username: Option[String] = None
      def password: Option[String] = None
      def authorisation: Option[String] = Some("basic")
      def projects: List[String] = List()
      def ecoMode: (Option[String], Option[String]) = (Some("10:00"), None)
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
        |
        |ui {
        |    eco-mode {
        |        # supply *both* start and end time to enable eco mode
        |        # start = "18:00"  # Uncomment to enable eco mode start time (hh:mm)
        |        # end = "07:00"    # Uncomment to enable eco mode end time (hh:mm)
        |    }
        |}
        |""".stripMargin
  }

  "Empty config file template" >> {
    Template(new ConfigFile {
      def url: Option[String] = None
      def username: Option[String] = None
      def password: Option[String] = None
      def authorisation: Option[String] = None
      def projects: List[String] = List()
      def ecoMode: (Option[String], Option[String]) = (None, None)
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
        |
        |ui {
        |    eco-mode {
        |        # supply *both* start and end time to enable eco mode
        |        # start = "18:00"  # Uncomment to enable eco mode start time (hh:mm)
        |        # end = "07:00"    # Uncomment to enable eco mode end time (hh:mm)
        |    }
        |}
        |""".stripMargin
  }

}
