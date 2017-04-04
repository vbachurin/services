import dependencies._
import io.gatling.sbt.GatlingPlugin

enablePlugins(GatlingPlugin)

name := "gatling-load-1"

organization := "pulse"

scalaVersion := "2.11.8"

resolvers ++= Seq(
  "Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"
)

scalacOptions := Seq(
  "-encoding", "UTF-8", "-target:jvm-1.8", "-deprecation",
  "-feature", "-unchecked", "-language:implicitConversions", "-language:postfixOps"
)

javaOptions in Gatling := overrideDefaultJavaOptions("-Xms1G", "-Xmx5G", "-Djsse.enableSNIExtension=false")

libraryDependencies ++= Seq(
  gatling.framework,
  gatling.highcharts
)
