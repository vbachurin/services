import sbt._, Keys._
import dependencies._

libraryDependencies ++= Seq (
  cats.all,
  fs2.core,
  logback.core,
  logger.core,
  finagle.core,
  finagle.server,
  finch.core, finch.circe, _test(finch.test),
  circe.core,
  _test(scalatest.core)
)

console.settings