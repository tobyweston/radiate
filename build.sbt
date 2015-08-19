name := "radiate"

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "bad.robot" % "simple-http" % "1.0-SNAPSHOT" % "compile" exclude("junit", "junit"),
  "commons-io" % "commons-io" % "1.3.2" % "compile",
  "io.argonaut" %% "argonaut" % "6.1" % "compile",
  "oncue.knobs" %% "core" % "3.3.0",
  "org.specs2" %% "specs2-core" % "3.6" % "test",
  "org.scalamock" %% "scalamock-specs2-support" % "3.2.2" % "test" excludeAll(ExclusionRule("org.specs2", "specs2_2.11"))
)

resolvers ++= Seq(
  "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases",
  "robotooling" at "http://www.robotooling.com/maven"
)

scalacOptions := Seq("-Xlint", "-Xfatal-warnings", "-deprecation", "-feature", "-language:implicitConversions,reflectiveCalls,higherKinds")