// release (see https://github.com/sbt/sbt-release)

import sbtrelease.ReleaseStateTransformations._

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  releaseStepTask(publish),
  setNextVersion,
  commitNextVersion,
  pushChanges
)