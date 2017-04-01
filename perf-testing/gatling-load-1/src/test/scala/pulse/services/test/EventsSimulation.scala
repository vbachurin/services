package pulse.services.test

import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class EventsSimulation extends Simulation {

  val httpProtocol = http
    .baseURL("http://192.168.99.100:8080")
    .inferHtmlResources()
    .acceptHeader("application/json")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.8,uk;q=0.6,ru;q=0.4")
    .contentTypeHeader("application/json")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")

  object Status {
    val run = exec(http("status request")
      .get("/v1/status")
      .check(status.is(200))
    )
  }

  val scn =
    scenario("EventsSimulation")
      .exec(Status.run)

  // raise the number of users during the period of time
  setUp(scn.inject(rampUsersPerSec(50) to (150) during (15 minutes))).protocols(httpProtocol)
}