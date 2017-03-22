organization in Global := "pulse"

scalaVersion in Global := "2.11.8"

settings.common

lazy val pulse_services = project.in(file(".")).aggregate(common, core, example)

lazy val common	 = project

lazy val core	   = project.dependsOn(common)
/*
lazy val core	   = project.dependsOn(common).settings(
  projectDependencies := {
    Seq(
      (projectID in common).value.exclude("org.typelevel", "cats")
    )
  }
)
*/

lazy val example = project.dependsOn(core)

/*
lazy val example = project.dependsOn(core).settings(
  projectDependencies := {
    Seq(
      (projectID in core).value.exclude("org.typelevel", "cats")
    )
  }
)
*/
