import sbtassembly.MergeStrategy._

name := "aerospike-spark"

version in ThisBuild := "1.1.7"

organization := "com.aerospike"


scalaVersion in ThisBuild := "2.11.8"

//javacOptions ++= Seq("-source", "1.7", "-target", "1.7")

parallelExecution in test := false

fork in test := true

val sparkVersion = "2.1.0"

libraryDependencies ++= Seq(
                             "org.apache.spark" %% "spark-core" % sparkVersion % Provided,
                             "org.apache.spark" %% "spark-sql" % sparkVersion % Provided,
                             "com.aerospike" % "aerospike-helper-java" % "1.0.6",
                             "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
                             "org.scalatest" %% "scalatest" % "2.2.1" % Test
                           )

resolvers ++= Seq("Local Maven" at Path.userHome.asFile.toURI.toURL + ".m2/repository")

cancelable in Global := true

assemblyMergeStrategy in assembly := {
  case x if Assembly.isConfigFile(x) =>
    MergeStrategy.concat
  case PathList(ps@_*) if Assembly.isReadme(ps.last) || Assembly.isLicenseFile(ps.last) =>
    MergeStrategy.rename
  case PathList("META-INF", "maven", "com.aerospike", "aerospike-client", "pom.properties") =>
    MergeStrategy.discard
  case PathList("META-INF", "maven", "com.aerospike", "aerospike-client", "pom.xml") =>
    MergeStrategy.discard
  case PathList("META-INF", xs@_*) =>
    xs.map(_.toLowerCase) match {
      case ("manifest.mf" :: Nil) | ("index.list" :: Nil) | ("dependencies" :: Nil) =>
        MergeStrategy.discard
      case ps@(x :: _) if ps.last.endsWith(".sf") || ps.last.endsWith(".dsa") =>
        MergeStrategy.discard
      case "plexus" :: _ =>
        MergeStrategy.discard
      case "services" :: _ =>
        MergeStrategy.filterDistinctLines
      case ("spring.schemas" :: Nil) | ("spring.handlers" :: Nil) =>
        MergeStrategy.filterDistinctLines
      case _ => MergeStrategy.deduplicate
    }
  case _ => MergeStrategy.first
}


licenses += ("Apache-2.0", url("http://opensource.org/licenses/Apache-2.0"))
bintrayOrganization := Some("tabmo")


