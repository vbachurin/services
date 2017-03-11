organization in Global := "pulse"

scalaVersion in Global := "2.11.8"

settings.common

lazy val pulse_services = project.in(file(".")).aggregate(common, core, example)

lazy val common		= project

lazy val core			= project.dependsOn(common)

lazy val example  = project.dependsOn(core)

