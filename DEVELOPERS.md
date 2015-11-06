# Developer's README

## Packaging

 * Run `sbt assembly` to assemble a mega-jar. It will output it to, for example, `radiate-scala/target/scala-2.11/radiate-x.jar`. It's executable via `java -jar radiate-x.jar`

 * Run `abt universal:package-bin` to create a zip which includes the above.


## Release Deployment

The general approach is to

 * Use the `sbt-release` plugin to increment the version and tag. Increment the version number of every release. No SNAPSHOTs. Ever.
 * Package a mega-jar with all the dependencies
 * Publish to a local maven repo ready to push to [robotooling](https://github.com/tobyweston/robotooling/tree/gh-pages)


To do release, run the following

    sbt release


This will prepare the JAR, tag and copy the JAR to a local maven repo. Next step is to publish the repo manually to the internet.