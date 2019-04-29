name := "spark-blas"

version := "0.1"

scalaVersion := "2.12.8"

resolvers ++= Seq(
  "sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases/",
  Resolver.jcenterRepo
)
libraryDependencies ++= Seq(
  "org.apache.spark"        %% "spark-mllib" % "2.4.0",
  "org.apache.spark"        %% "spark-sql"   % "2.4.0",
  "com.github.fommil.netlib" % "all"         % "1.1.2" pomOnly(),
)

assemblyMergeStrategy in assembly := {
  case PathList("javax", "servlet", xs @ _*) => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".html" => MergeStrategy.first
  case "log4j.propreties" => MergeStrategy.first
  // ----
  // required for spark-sql to read different data types (e.g. parquet/orc/csv...)
  // ----
  case PathList("META-INF", "services", xs @ _*) => MergeStrategy.first
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case n if n.startsWith("reference.conf") => MergeStrategy.concat
  case n if n.endsWith(".conf") => MergeStrategy.concat
  case x => MergeStrategy.first
}

mainClass in assembly := Some("me.rotemfo.SparkBLASApp")