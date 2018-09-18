
name := "radiate"

organization := "bad.robot"

assemblyJarName in assembly := s"${name.value}-${version.value}.jar"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "com.simple-http" % "simple-http" % "1.3" % "compile" exclude("junit", "junit"),
  "commons-io" % "commons-io" % "1.3.2" % "compile",
  "io.argonaut" %% "argonaut" % "6.2" % "compile",
  "io.verizon.knobs" %% "core" % "6.0.33",
  "com.codecommit" %% "shims" % "1.2.1",
  "org.specs2" %% "specs2-core" % "4.2.0" % "test",
  "org.typelevel" %% "scalaz-specs2" % "0.5.2" % "test",
  "org.scalamock" %% "scalamock-specs2-support" % "3.6.0" % "test"
)

scalacOptions := Seq("-Xlint", "-Xfatal-warnings", "-deprecation", "-feature", "-language:implicitConversions,reflectiveCalls,higherKinds")
