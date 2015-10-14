# Developer's README

## Packaging

* Run `sbt assembly` to assemble a mega-jar. It will outout it to, for example, `radiate-scala/target/scala-2.11/radiate-x.jar`. It's executable via `java -jar radiate-x.jar`

* Run `abt universal:package-bin` to create a zip which includes the above.

