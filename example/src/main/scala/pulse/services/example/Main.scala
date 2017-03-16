package pulse.services
package example

import com.twitter.finagle.{Http, ListeningServer}
import core.ServerApp


object Main extends ServerApp {
  val useTaskApi = flag("use.task.api", false, "true is use task api, otherwise use future api")

  def server: ListeningServer = Http.server.serve(":8080", ExampleApi(useTaskApi()).apiService)
}
