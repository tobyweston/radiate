name := "radiate"

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "bad.robot" % "simple-http" % "1.0-SNAPSHOT" % "compile" exclude("junit", "junit"),
  "com.google.code.gson" % "gson" % "2.3.1" % "compile",
  "com.googlecode.totallylazy" % "totallylazy" % "1199" % "compile",
  "commons-io" % "commons-io" % "1.3.2" % "compile",
  "io.argonaut" %% "argonaut" % "6.1" % "compile",
  "org.yaml" % "snakeyaml" % "1.15" % "compile",
  "org.specs2" %% "specs2-core" % "3.6" % "test",
  "org.scalamock" %% "scalamock-specs2-support" % "3.2.2" % "test",
  "org.jmock" % "jmock-junit4" % "2.8.1" % "test",
  "junit" % "junit" % "4.12" % "test"
)

resolvers ++= Seq(
  "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases",
  "robotooling" at "http://www.robotooling.com/maven",
  "bodar" at "http://repo.bodar.com"
)

compileOrder := CompileOrder.JavaThenScala
