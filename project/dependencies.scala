import sbt._, Keys._

object dependencies {

  def _test     (module: ModuleID): ModuleID = module % "test"
  def _provided (module: ModuleID): ModuleID = module % "provided"

  object versions {
    val scalatest = "2.2.6"
    val shapeless = "2.3.2"
    val simulacrum = "0.10.0"
    val commons = "3.5"
    val cats = "0.9.0"
    val fs2 = "0.9.4"
    val logback = "1.1.7"
    val scalalog = "3.5.0"
    val finagle = "6.43.0"
    val finch = "0.13.0"
    val circe = "0.7.0"
    object twitter {
      val server  = "1.28.0"
      val finagle = "6.43.0"
    }
  }

  object finch {
    val core  = "com.github.finagle" %% "finch-core"  % versions.finch
    val circe = "com.github.finagle" %% "finch-circe" % versions.finch
    val test  = "com.github.finagle" %% "finch-test"  % versions.finch
  }

  object circe {
    val core    = "io.circe" %% "circe-core"    % versions.circe
    val generic = "io.circe" %% "circe-generic" % versions.circe
    val parser  = "io.circe" %% "circe-parser"  % versions.circe
  }

  object finagle {
    val core   = "com.twitter" %% "finagle-http"   % versions.twitter.finagle
    val server = "com.twitter" %% "twitter-server" % versions.twitter.server
  }

  object logback {
    val core = "ch.qos.logback" % "logback-classic" % versions.logback
  }

  object logger {
    val core = "com.typesafe.scala-logging" %% "scala-logging" % versions.scalalog
  }

  object fs2 {
    val core = "co.fs2" %% "fs2-core" % versions.fs2
    val io   = "co.fs2" %% "fs2-io"   % versions.fs2
  }

  object cats {
    val all = "org.typelevel" %% "cats" % versions.cats
  }

  object scalatest {
    val core = "org.scalatest" %% "scalatest" % versions.scalatest
  }

  object shapeless {
    val core = "com.chuusai" %% "shapeless" % versions.shapeless
  }

  object simulacrum {
    val core = "com.github.mpilquist" %% "simulacrum" % versions.simulacrum
  }

  object apache {
    val lang = "org.apache.commons" % "commons-lang3" % versions.commons
  }
}
