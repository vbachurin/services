import sbt._, Keys._
import sbtassembly._, AssemblyKeys._, AssemblyPlugin._
import com.typesafe.sbt.SbtNativePackager._

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

  lazy val sbtAssemblySettings = Seq(
    assemblyMergeStrategy in assembly := {
      case PathList("META-INF", xs@_*) => MergeStrategy.discard
      case "BUILD" => MergeStrategy.discard
      case _ => MergeStrategy.deduplicate
    }
  )

  def sbtAssembly = common ++ sbtAssemblySettings

  lazy val sbtDockerSettings = Seq(
    // Remove all jar mappings in universal and append the fat jar
    mappings in Universal := {
      val universalMappings = (mappings in Universal).value
      val fatJar = (assembly in Compile).value
      val filtered = universalMappings.filter {
        case (file, name) => !name.endsWith(".jar")
      }
      filtered :+ (fatJar -> ("lib/" + fatJar.getName))
    }
  )

  def docker = sbtAssembly ++ sbtDockerSettings
}
