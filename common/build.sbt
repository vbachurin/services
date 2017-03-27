import sbt._, Keys._
import dependencies._

libraryDependencies ++= Seq (
  cats.all,
  fs2.core,
  logback.core,
  logger.core,
  scopt.core,
  _test(scalatest.core)
)

console.settings

assemblyMergeStrategy in assembly := {
  //  case PathList("cats", "kernel", xs @ _*)         => MergeStrategy.deduplicate
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case "BUILD" => MergeStrategy.discard
  case _ => MergeStrategy.deduplicate
}