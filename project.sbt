organization in Global := "pulse"

scalaVersion in Global := "2.11.8"

settings.common

lazy val pulse_services = project.in(file(".")).aggregate(common, core)

lazy val common			= project

lazy val core			= project.dependsOn(common)

