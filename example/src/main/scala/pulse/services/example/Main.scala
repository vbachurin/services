package pulse.services
package example

import com.twitter.finagle.{Http, ListeningServer}
import core.ServerApp


object Main extends ServerApp {
  val server: ListeningServer = Http.server.serve(":8080", ExampleApi.apiService)
}
