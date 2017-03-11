import sbt._, Keys._

object settings {

  private val compilerFlags = Seq(
    "-deprecation",
    "-encoding", "UTF-8",
    "-unchecked",
    "-feature",
    "-language:higherKinds",
    "-language:implicitConversions",
    "-language:postfixOps",
    "-Xfatal-warnings"
  )

  def compiler = Seq(
    scalacOptions in Compile ++= compilerFlags
  )

  def common = console.settings ++ compiler
}
