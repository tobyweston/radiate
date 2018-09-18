// publish (see https://github.com/sbt/sbt-assembly)

val publishFolder = "/Users/toby/code/github/robotooling/maven/"

publishTo := Some(Resolver.file("file", new File(publishFolder)))

addArtifact(artifact in(Compile, assembly), assembly)


// Remove ScalaDoc generation

sources in(Compile, doc) := Seq.empty
publishArtifact in(Compile, packageDoc) := false
