
name := "radiate"

organization := "bad.robot"

assemblyJarName in assembly := s"${name.value}-${version.value}.jar"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "bad.robot" % "simple-http" % "1.0" % "compile" exclude("junit", "junit"),
  "commons-io" % "commons-io" % "1.3.2" % "compile",
  "io.argonaut" %% "argonaut" % "6.2" % "compile",
  "io.verizon.knobs" %% "core" % "6.0.33",
  "com.codecommit" %% "shims" % "1.2.1",
  "org.specs2" %% "specs2-core" % "4.2.0" % "test",
  "org.typelevel" %% "scalaz-specs2" % "0.5.2" % "test",
  "org.scalamock" %% "scalamock-specs2-support" % "3.6.0" % "test"
)

resolvers ++= Seq(
  "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases",
  "Oncue Bintray Repo" at "http://dl.bintray.com/oncue/releases",
  "robotooling" at "http://www.robotooling.com/maven"
)

scalacOptions := Seq("-Xlint", "-Xfatal-warnings", "-deprecation", "-feature", "-language:implicitConversions,reflectiveCalls,higherKinds")


// Remove ScalaDoc generation

sources in(Compile, doc) := Seq.empty
publishArtifact in(Compile, packageDoc) := false


// publish (see https://github.com/sbt/sbt-assembly)

//val publishFolder = "/Users/toby/Workspace/robotooling/maven/"
//
//publishTo := Some(Resolver.file("file", new File(publishFolder)))
//
//addArtifact(artifact in(Compile, assembly), assembly)



// proguard - reduce 30 MB artifact to something like 5.5 MB (https://github.com/sbt/sbt-proguard)

//proguardSettings
//
//ProguardKeys.proguardVersion in Proguard := "5.2.1"
//
//ProguardKeys.options in Proguard ++= Seq(
//  "-dontnote",
//  "-dontwarn scala.**",
//  "-dontwarn com.google.code.tempusfugit.**",
//  "-dontwarn org.apache.**",
//  "-dontwarn scodec.bits.**",
//  "-dontwarn scalaparsers.**",
//  "-ignorewarnings",
//  "-dontobfuscate",
//  "-printusage unused-code.txt",
//  """
//    -keep public class bad.robot.** {
//      *;
//    }
//
//    -keep public class org.apache.commons.** {
//      *;
//     }
//
//    -keepclassmembers class * {
//      ** MODULE$;
//    }
//
//  """
//)
//
//ProguardKeys.options in Proguard += ProguardOptions.keepMain("bad.robot.radiate.Main")
//
//javaOptions in(Proguard, ProguardKeys.proguard) := Seq("-Xmx2G")
//
//ProguardKeys.inputs in Proguard := (dependencyClasspath in Compile).value.files
//
//ProguardKeys.filteredInputs in Proguard ++= ProguardOptions.noFilter((packageBin in Compile).value)
//
//
//
//// release (see https://github.com/sbt/sbt-release)
//
//import sbtrelease.ReleaseStateTransformations._
//
//releaseProcess := Seq[ReleaseStep](
//  checkSnapshotDependencies,
//  inquireVersions,
//  runClean,
//  runTest,
//  setReleaseVersion,
//  commitReleaseVersion,
//  tagRelease,
//  releaseStepTask(publish),
//  setNextVersion,
//  commitNextVersion,
//  pushChanges
//)