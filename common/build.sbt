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