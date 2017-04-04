import sbt._

object dependencies {

  def _test     (module: ModuleID): ModuleID = module % "test"
  def _provided (module: ModuleID): ModuleID = module % "provided"

  object versions {
    val gatling = "2.2.3"
  }

  object gatling {
    val framework =  _test("io.gatling" % "gatling-test-framework"  % versions.gatling)
    val highcharts = _test("io.gatling.highcharts" % "gatling-charts-highcharts" % versions.gatling)
  }

}
